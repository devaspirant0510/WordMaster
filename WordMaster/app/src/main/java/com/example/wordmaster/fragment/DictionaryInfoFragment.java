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

import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.adapter.DictionaryInfoAdapter;
import com.example.wordmaster.callback.DialogUpdateCallback;
import com.example.wordmaster.callback.DictionaryFragmentCallBack;
import com.example.wordmaster.callback.DictionaryListCallBack;
import com.example.wordmaster.data.firebase.UserDictionary;
import com.example.wordmaster.data.recycler.DictionaryListItem;
import com.example.wordmaster.data.recycler.DictionaryWordItem;
import com.example.wordmaster.databinding.FragmentDictionaryInfoBinding;
import com.example.wordmaster.define.Define;
import com.example.wordmaster.dialog.custom.CreateWordDialog;
import com.example.wordmaster.dialog.custom.DictionaryUpdateDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class DictionaryInfoFragment extends Fragment implements DictionaryFragmentCallBack {
    private MainActivity activity;
    private FragmentDictionaryInfoBinding mb;
    protected static String TAG = "DictionaryInfoFragment";
    private String dictInfoTitle,dictInfoOption,dictDescription,dictHashTag;
    private DictionaryInfoAdapter adapter;
    private ArrayList<DictionaryWordItem> wordList = new ArrayList<>();
    private int dictCount;
    private String setMode = "add";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: " );
        mb = FragmentDictionaryInfoBinding.inflate(getLayoutInflater());
        activity = (MainActivity)getActivity();
        // 프레그먼트 전환될때 argument 에 정보 들어있는지 확인
        adapter = new DictionaryInfoAdapter(getContext());
        mb.wordList.setAdapter(adapter);
        Bundle bundle = getArguments();
        if (bundle!=null){
            dictInfoTitle = bundle.getString("Title");
            dictInfoOption = bundle.getString("Option");
            dictCount = bundle.getInt("Count");
            dictDescription = bundle.getString("Description");
            dictHashTag = bundle.getString("HashTag");
        }
        readWordList();
    }
    // 유저 id -> 단어장 -> list 를 참조하여 단어 불러옴
    private void readWordList(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(Define.USER);
        reference.child(dictInfoTitle).child("list").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (setMode.equals("add")){
                    Log.e(TAG, "onChildAdded: "+snapshot.getChildrenCount() );
                    String item = snapshot.getValue().toString();
                    item = item.replaceAll("[}{]","");
                    String[] splitWord = item.split(",");
                    String strEng = splitWord[0].split("=")[1];
                    String strKor = splitWord[1].split("=")[1];
                    adapter.addItem(new DictionaryWordItem(strKor,strEng));
                    adapter.notifyDataSetChanged();
                    mb.progressBar.setProgress(adapter.getItemCount());
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e(TAG, "onChildAdded: "+snapshot.getChildrenCount() );
                String item = snapshot.getValue().toString();
                item = item.replaceAll("[}{]","");
                String[] splitWord = item.split(",");
                String strEng = splitWord[0].split("=")[1];
                String strKor = splitWord[1].split("=")[1];
                adapter.addItem(new DictionaryWordItem(strKor,strEng));
                adapter.notifyDataSetChanged();
                mb.progressBar.setProgress(adapter.getItemCount());
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.setDictionaryListCallBack(this);
        init();
        return mb.getRoot();
    }

    private void init() {
        mb.progressState.setText(adapter.getItemCount()+"/"+dictCount);
        mb.progressBar.setMax(dictCount);
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
                DictionaryUpdateDialog dialog = new DictionaryUpdateDialog(getContext(),Define.DIALOG_DICT_WORD);
                dialog.setDialogUpdateCallback(new DialogUpdateCallback() {
                    @Override
                    public void setOnClickUpdateButton() {
                    }
                    @Override
                    public void setOnClickDeleteButton() {
                        setMode="delete";
                        deleteFireBaseData(pos);
                        mb.progressState.setText(adapter.getItemCount()+"/"+dictCount);
                    }
                });
                dialog.show();
            }
        });
        // 단어 추가 버튼을 눌렀을때
        if (wordList.size()==dictCount){
            Toast.makeText(getContext(),"더 이상 추가할수 없습니다.",Toast.LENGTH_SHORT).show();
        }
        else{
            mb.btnDictInfoCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getContext()!=null){
                        setMode="add";
                        CreateWordDialog dialog = new CreateWordDialog(getContext(),wordList,adapter,dictInfoTitle);
                        dialog.show();
                    }
                }
            });

        }

    }

    /**
     * 포지션 값을 받아 해당 DB 삭제
     * @param pos : 포지션 값
     */
    public void deleteFireBaseData(int pos){
        // 어댑터에 있는 리스트 가져와 해당 값 삭제
        ArrayList<DictionaryWordItem> list = adapter.wordList;
        adapter.removeItem(list.get(pos));
        adapter.notifyDataSetChanged();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Define.USER);
        // 파이어베이스에서 list 통째로 삭제후 삭제된 리스트 setValue 시킴
        myRef.child(dictInfoTitle).child("list").setValue(null);
        myRef.child(dictInfoTitle).child("list").setValue(adapter.wordList);



    }


    @Override
    public void sendInfoData(String title, String option, int count) {
        mb.dictInfoTitle.setText(title);
        mb.dictInfoOption.setText(option);
    }
}
