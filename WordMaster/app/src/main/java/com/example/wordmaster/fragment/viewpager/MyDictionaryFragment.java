package com.example.wordmaster.fragment.viewpager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.Define.Define;
import com.example.wordmaster.activities.LoginActivity;
import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.adapter.DictionaryListAdapter;
import com.example.wordmaster.callback.BottomSheetCallBack;
import com.example.wordmaster.callback.DialogUpdateCallback;
import com.example.wordmaster.callback.DictionaryListCallBack;
import com.example.wordmaster.callback.SendDataToActivity;
import com.example.wordmaster.databinding.FragmentDictionaryBinding;
import com.example.wordmaster.dialog.bottomsheet.CreateDictionarySheetDialog;
import com.example.wordmaster.dialog.custom.DictionaryUpdateDialog;
import com.example.wordmaster.model.firebase.UserDictionary;
import com.example.wordmaster.model.recycler.DictionaryListItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyDictionaryFragment extends Fragment implements BottomSheetCallBack {
    private FragmentDictionaryBinding mb;
    protected static final String TAG = "DictionaryFragment";
    private CreateDictionarySheetDialog dialog;
    private MainActivity activity;
    private DictionaryListAdapter adapter;
    private SendDataToActivity sendDataToActivity = null;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mMyRef;
    private String title,spUserId,spUserEmail,spUserName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        mDatabase = FirebaseDatabase.getInstance();
        mMyRef = mDatabase.getReference();
        SharedPreferences sharedPreferences = activity.getSharedPreferences("LoginInformation", Context.MODE_PRIVATE);
        spUserId = sharedPreferences.getString("userId","");
        spUserEmail = sharedPreferences.getString("userEmail","");
        spUserName = sharedPreferences.getString("userNickName","");


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SendDataToActivity) {
            sendDataToActivity = (SendDataToActivity) context;

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.BottomSheetCallBack(this);
        mb = FragmentDictionaryBinding.inflate(getLayoutInflater());
        init();
        View root = mb.getRoot();
        readDB();
        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // 파이어베이스 DB 단어장 리스트 읽어오기
    private void readDB() {
        Log.e(TAG, "readDB: "+spUserId );
        mMyRef.child("WordStore").child(spUserId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserDictionary userDictionary = snapshot.getValue(UserDictionary.class);
                title = userDictionary.getTitle();
                // 읽어와서 어댑터에 추가
                adapter.addItem(new DictionaryListItem(
                        userDictionary.getTitle(),
                        String.valueOf(userDictionary.getMaxCount()),
                        userDictionary.getDescription(),
                        LoginActivity.USER,
                        userDictionary.getOption(),
                        1
                ));
                mb.dictionaryList.scrollToPosition(adapter.getItemCount()-1);

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
     * 파베 DB 에 단어장을 추가함
     *
     * @param title      : 단어장 제목
     * @param dictionary : 단어장 정보 (설명,해시태그,최대개수,공개여부,제목)
     */
    private void createFirebaseReadDatabase(String title, UserDictionary dictionary) {
        mMyRef.child("WordStore").child(spUserId).child(title).setValue(dictionary);

    }

    private void init() {
        adapter = new DictionaryListAdapter(getContext());
        mb.dictionaryList.setAdapter(adapter);
        //추가 플로팅 버튼을 눌렀을때
        mb.createDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 바텀 시트 다이얼로그 뛰워서 단어장 정보 입력
                dialog = new CreateDictionarySheetDialog(activity);
                if (getFragmentManager() != null) {
                    dialog.show(getFragmentManager(), "add Dictionary");
                }
            }
        });
        // 리사이클러뷰의 아이템을 클릭했을때
        adapter.setDictionaryListCallBack(new DictionaryListCallBack() {
            // 클릭 => 해당 단어장의 세부정보 보여주는 화면으로 전환
            @Override
            public void onClick(View v, int pos) {
                DictionaryListItem item = adapter.getItem(pos);
                // 메인액티비티에 정보보냄 프레그먼트 트랜잭션
                sendDataToActivity.sendDictData(item.getDictionaryTitle(), item.getDictOption(), item.getDictionaryHost(), Integer.parseInt(item.getDictionaryMaxCount()));
                activity.changeFragment(Define.DICTIONARY_INFO_FRAGMENT);
            }
            // 롱클릭 => 수정,삭제 다이얼로그
            @Override
            public void onLongClick(View v, int pos) {
                DictionaryUpdateDialog dialog = new DictionaryUpdateDialog(getContext(), Define.DIALOG_DICT_WORD);

                dialog.setDialogUpdateCallback(new DialogUpdateCallback() {
                    @Override
                    // 다이얼로그에서 수정버튼을 눌렀을때
                    public void setOnClickUpdateButton() {
                        // TODO : 바텀시트띄워서 정보수정

                    }

                    @Override
                    public void setOnClickDeleteButton() {
                        // TODO : 해당 단어장 삭제후 어댑터 다시 업데이트
                    }
                });
                dialog.show();
            }
        });

    }

    // 바텀시트에서 입력한 정보 콜백으로 받아옴
    @Override
    public void createDialogGetData(String title, int count, int currentCount,String description, String hashTag, String DictOption) {
        createFirebaseReadDatabase(title, new UserDictionary(DictOption, title, count,currentCount, description, hashTag));

    }
}
