package com.example.wordmaster.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.SharedManger;
import com.example.wordmaster.Define.Util;
import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.adapter.DictionaryInfoAdapter;
import com.example.wordmaster.callback.DialogUpdateCallback;
import com.example.wordmaster.callback.DictionaryFragmentCallBack;
import com.example.wordmaster.callback.DictionaryListCallBack;
import com.example.wordmaster.callback.SendDataToActivity;
import com.example.wordmaster.databinding.FragmentDictionaryInfoBinding;
import com.example.wordmaster.dialog.bottomsheet.MyTestOptionBottomSheetDialog;
import com.example.wordmaster.dialog.custom.CreateWordDialog;
import com.example.wordmaster.dialog.custom.DictionaryUpdateDialog;
import com.example.wordmaster.model.recycler.DictionaryWordItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private SendDataToActivity sendDataToActivity = null;
    public void setSendDataToActivity(SendDataToActivity callback){
        this.sendDataToActivity = callback;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentDictionaryInfoBinding.inflate(getLayoutInflater());

        activity = (MainActivity) getActivity();
        spUserId = SharedManger.loadData(Const.SHARED_USER_ID,"");
        spUserEmail = SharedManger.loadData(Const.SHARED_USER_EMAIL,"");
        spUserName = SharedManger.loadData(Const.SHARED_USER_NAME,"");

        // 프레그먼트 트랜잭션 하면서 넘긴 값들 가져옴
        Bundle bundle = getArguments();
        if (bundle != null) {
            dictInfoTitle = bundle.getString("Title");
            dictInfoOption = bundle.getString("Option");
            dictCount = bundle.getInt("Count");
            dictDescription = bundle.getString("Description");
            dictHashTag = bundle.getString("HashTag");
            dictPosition = bundle.getInt("Position");
            roomKey = bundle.getString("RoomKey");
        }
        adapter = new DictionaryInfoAdapter(getContext());
        mb.wordList.setAdapter(adapter);

        readWordList();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.setDictionaryListCallBack(this);
        init();
        return mb.getRoot();
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
                DictionaryUpdateDialog dialog = new DictionaryUpdateDialog(getContext(), Const.DIALOG_DICT_WORD);
                dialog.setDialogUpdateCallback(new DialogUpdateCallback() {
                    // 수정 버튼
                    @Override
                    public void setOnClickUpdateButton() {
                        CreateWordDialog dialog = new CreateWordDialog(getContext(), spUserId, wordList, adapter, dictInfoTitle, Const.UPDATE, roomKey);
                        dialog.show();
                        mb.wordList.scrollToPosition(pos);
                        dialog.setUpdateWord(adapter.wordList.get(pos).getEng(), adapter.wordList.get(pos).getKor(), pos);
                        dialog.setOnClickListener(new CreateWordDialog.OnClickListener() {
                            @Override
                            public void onSubmitClick(String eng, String kor, int mode) {
                                DictionaryWordItem item = adapter.getItem(pos);
                                item.setKor(kor);
                                item.setEng(eng);
                                adapter.changeItem(pos,item);
                                Util.myRefWord.child(spUserId).child(roomKey).child("list").child(item.getRoomKey()).setValue(item);
                                adapter.notifyItemChanged(pos);

                            }
                        });


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
                        CreateWordDialog dialog = new CreateWordDialog(getContext(), spUserId, wordList,
                                                                                adapter,dictInfoTitle,
                                                                                Const.CREATE, roomKey);
                        dialog.setOnClickListener(new CreateWordDialog.OnClickListener() {
                            @Override
                            public void onSubmitClick(String eng, String kor, int mod) {
                                DatabaseReference pushRef = Util.myRefWord.child(spUserId).child(roomKey).child(Const.FIREBASE_REFERENCE_WORD_LIST)
                                        .push();
                                String roomKey = pushRef.getKey();
                                DictionaryWordItem item = new DictionaryWordItem(kor,eng);
                                item.setRoomKey(roomKey);
                                pushRef.setValue(item);
                            }
                        });
                        dialog.show();
                    }
                }
            }
        });
        // 테스트 버튼을 눌렀을때
        mb.btnDictInfoTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getItemCount()>0){

                    MyTestOptionBottomSheetDialog dialog = new MyTestOptionBottomSheetDialog(getContext());
                    dialog.setCallBackOption(new MyTestOptionBottomSheetDialog.CallBackOption() {
                        @Override
                        public void callBack(int option) {

                            sendDataToActivity.sendTestingData(spUserId,roomKey,dictCount,option,spUserName,dictInfoTitle);
                            activity.changeFragment(Const.TEST_MY_FRAGMENT);
                        }
                    });
                    dialog.show();
                }else{
                    Toast.makeText(getContext(),"단어를 추가해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    // 유저 id -> 단어장 -> list 를 참조하여 단어 불러옴
    public void readWordList() {
        Util.myRefWord.child(spUserId).child(roomKey)
                .child(Const.FIREBASE_REFERENCE_WORD_LIST).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // 단어장에서 단어 하나하나 불러오기
                DictionaryWordItem item = snapshot.getValue(DictionaryWordItem.class);
                if (item != null) {
                    item.setIndex(adapter.getItemCount()+1);
                    adapter.addItem(item);

                    mb.wordList.scrollToPosition(adapter.getItemCount()-1);
                    // 프로그래스바에서 현재 단어장 개수 표시
                    mb.progressBar.setProgress(adapter.getItemCount());
                    mb.progressState.setText(adapter.getItemCount()+"/"+dictCount);
                }

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


    /**
     * 포지션 값을 받아 해당 DB 삭제
     *
     * @param pos : 포지션 값
     */
    public void deleteFireBaseData(int pos) {
        // 어댑터에 있는 리스트 가져와 해당 값 삭제
        DictionaryWordItem item = adapter.getItem(pos);
        String wordKey = item.getRoomKey();
        Log.e(TAG, "deleteFireBaseData: "+roomKey );
        // DB 에서 삭제
        Util.myRefWord.child(spUserId).child(roomKey).child("list").child(wordKey).setValue(null);
        // UI 에서 삭제
        adapter.removeItem(pos);
        adapter.notifyItemRemoved(pos);
        updateIndex(pos);


    }
    private void updateIndex(int pos){
        for (int i = pos; i < adapter.getItemCount(); i++) {
            DictionaryWordItem item = adapter.getItem(i);
            item.setIndex(i+1);
            adapter.changeItem(i,item);
            adapter.notifyItemChanged(i);

        }
    }

    @Override
    public void sendInfoData(String title, String option, int count) {
        mb.dictInfoTitle.setText(title);
        mb.dictInfoOption.setText(option);
    }
}
