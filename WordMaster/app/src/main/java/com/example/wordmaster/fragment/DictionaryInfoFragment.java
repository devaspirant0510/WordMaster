package com.example.wordmaster.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.Define.Define;
import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.adapter.DictionaryInfoAdapter;
import com.example.wordmaster.callback.DialogUpdateCallback;
import com.example.wordmaster.callback.DictionaryFragmentCallBack;
import com.example.wordmaster.callback.DictionaryListCallBack;
import com.example.wordmaster.databinding.FragmentDictionaryInfoBinding;
import com.example.wordmaster.dialog.custom.CreateWordDialog;
import com.example.wordmaster.dialog.custom.DictionaryUpdateDialog;
import com.example.wordmaster.model.recycler.DictionaryWordItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DictionaryInfoFragment extends Fragment implements DictionaryFragmentCallBack {
    private MainActivity activity;
    private FragmentDictionaryInfoBinding mb;
    protected static String TAG = "DictionaryInfoFragment";
    private String dictInfoTitle, dictInfoOption, dictDescription, dictHashTag;
    private DictionaryInfoAdapter adapter;
    private ArrayList<DictionaryWordItem> wordList = new ArrayList<>();
    private int dictCount, dictPosition;
    private String roomKey;
    private String setMode = "add", spUserId, spUserEmail, spUserName;
    // 파베
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = mDatabase.getReference("WordStore");

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentDictionaryInfoBinding.inflate(getLayoutInflater());
        activity = (MainActivity) getActivity();
        SharedPreferences sharedPreferences = activity.getSharedPreferences("LoginInformation", Context.MODE_PRIVATE);
        spUserId = sharedPreferences.getString("userId", "");
        spUserEmail = sharedPreferences.getString("userEmail", "");
        spUserName = sharedPreferences.getString("userNickName", "");
        // 프레그먼트 전환될때 argument 에 정보 들어있는지 확인
        adapter = new DictionaryInfoAdapter(getContext());
        mb.wordList.setAdapter(adapter);
        Bundle bundle = getArguments();
        if (bundle != null) {
            dictInfoTitle = bundle.getString("Title");
            dictInfoOption = bundle.getString("Option");
            dictCount = bundle.getInt("Count");
            dictDescription = bundle.getString("Description");
            dictHashTag = bundle.getString("HashTag");
            dictPosition = bundle.getInt("Position");
        }
        readWordList();
    }

    // 유저 id -> 단어장 -> list 를 참조하여 단어 불러옴
    public void readWordList() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        final int[] current = {0};
        reference.child("WordStore").child(spUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    if (dictPosition == current[0]) {
                        roomKey = data.getKey();

                        Log.e(TAG, "onDataChange: " + roomKey);
                        final int[] idx = {0};
                        myRef.child(spUserId).child(roomKey).child("list").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                DictionaryWordItem item = snapshot.getValue(DictionaryWordItem.class);
                                Log.e(TAG, "onChildAdded: " + snapshot.getValue());
                                idx[0] += 1;
                                mb.progressState.setText(adapter.getItemCount() + 1 + "/" + dictCount);
                                myRef.child(spUserId).child(roomKey).child("currentCount").setValue(adapter.getItemCount() + 1);
                                mb.progressBar.setProgress(adapter.getItemCount());
                                adapter.addItem(item);
                                adapter.notifyItemInserted(idx[0] - 1);

                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    current[0] += 1;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.setDictionaryListCallBack(this);
        init();
        return mb.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void init() {
        mb.progressBar.setMax(dictCount);
        mb.progressState.setText(adapter.getItemCount() + "/" + dictCount);
        mb.dictInfoTitle.setText(dictInfoTitle);
        mb.dictInfoOption.setText(dictInfoOption);
        // 어댑터의 아이템 단어 를 클릭했을때
        adapter.setDictionaryListCallBack(new DictionaryListCallBack() {
            @Override
            public void onClick(View v, int pos) {
            }

            // 롱클릭  -> 수정 또는 삭제 여부 묻는 다이얼로그
            @Override
            public void onLongClick(View v, int pos) {
                DictionaryUpdateDialog dialog = new DictionaryUpdateDialog(getContext(), Define.DIALOG_DICT_WORD);
                dialog.setDialogUpdateCallback(new DialogUpdateCallback() {
                    @Override
                    public void setOnClickUpdateButton() {
                        CreateWordDialog dialog = new CreateWordDialog(getContext(), spUserId, wordList, adapter, dictInfoTitle, Define.UPDATE, roomKey);
                        dialog.show();
                        dialog.setUpdateWord(adapter.wordList.get(pos).getEng(), adapter.wordList.get(pos).getKor(), pos);
                        adapter.wordList.clear();
                        adapter.notifyDataSetChanged();
                        readWordList();
                        //readWordList();
                        adapter.notifyItemChanged(pos);

                    }

                    @Override
                    public void setOnClickDeleteButton() {
                        setMode = "delete";
                        deleteFireBaseData(pos);
                        mb.progressState.setText(adapter.getItemCount() + "/" + dictCount);

                    }
                });
                dialog.show();
            }
        });
        // 단어 추가 버튼을 눌렀을때
        mb.btnDictInfoCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() != null) {
                    setMode = "add";
                    if (adapter.getItemCount() == dictCount) {
                        Toast.makeText(getContext(), "더 이상 추가할수 없습니다.", Toast.LENGTH_SHORT).show();
                    }else {
                        CreateWordDialog dialog = new CreateWordDialog(getContext(), spUserId, wordList, adapter, dictInfoTitle, Define.CREATE, roomKey);
                        Log.e(TAG, "onClick: " + adapter.wordList);
                        dialog.show();
                    }
                }
            }
        });


    }

    /**
     * 포지션 값을 받아 해당 DB 삭제
     *
     * @param pos : 포지션 값
     */
    public void deleteFireBaseData(int pos) {
        // 어댑터에 있는 리스트 가져와 해당 값 삭제
        ArrayList<DictionaryWordItem> list = adapter.wordList;
        adapter.removeItem(list.get(pos));
        adapter.notifyDataSetChanged();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("WordStore");
        // 파이어베이스에서 list 통째로 삭제후 삭제된 리스트 setValue 시킴
        myRef.child(spUserId).child(dictInfoTitle).child("list").setValue(null);
        myRef.child(spUserId).child(dictInfoTitle).child("list").setValue(adapter.wordList);


    }


    @Override
    public void sendInfoData(String title, String option, int count) {
        mb.dictInfoTitle.setText(title);
        mb.dictInfoOption.setText(option);
    }
}
