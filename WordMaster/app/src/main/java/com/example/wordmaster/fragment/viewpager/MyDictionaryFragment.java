package com.example.wordmaster.fragment.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.SharedManger;
import com.example.wordmaster.Define.Util;
import com.example.wordmaster.R;
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

public class MyDictionaryFragment extends Fragment implements BottomSheetCallBack {
    private FragmentDictionaryBinding mb;
    protected static final String TAG = "DictionaryFragment";
    private CreateDictionarySheetDialog dialog;
    private MainActivity activity;
    private DictionaryListAdapter adapter;
    private SendDataToActivity sendDataToActivity = null;
    private String title, spUserId, spUserEmail, spUserName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        // sharedManger 에서 저장된 정보 가져옴
        spUserId = SharedManger.loadData(Const.SHARED_USER_ID,"");
        spUserEmail = SharedManger.loadData(Const.SHARED_USER_EMAIL,"");
        spUserName = SharedManger.loadData(Const.SHARED_USER_NAME,"");
        Toast.makeText(getContext(),"oncreate",Toast.LENGTH_SHORT).show();
        mb = FragmentDictionaryBinding.inflate(getLayoutInflater());
        init();
        readWordDictList();


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
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
        actionBar.setSubtitle(null);
        return mb.getRoot();
    }


    private void removeWordDictList(String roomKey, int pos){
        Util.myRefWord.child(spUserId).child(roomKey).setValue(null);
        adapter.removeItem(pos);
        adapter.notifyItemRemoved(pos);
    }

    // 파이어베이스 DB 단어장 리스트 읽어오기
    private void readWordDictList() {
        Util.myRefWord.child(spUserId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserDictionary userDictionary = snapshot.getValue(UserDictionary.class);
                Log.e(TAG, "onChildAdded: "+userDictionary );
                // 읽어와서 어댑터에 추가
                    adapter.addItem(new DictionaryListItem(
                            userDictionary.getTitle(),
                            String.valueOf(userDictionary.getMaxCount()),
                            userDictionary.getDescription(),
                            SharedManger.loadData(Const.SHARED_USER_NAME,""),
                            userDictionary.getOption(),
                            userDictionary.getRoomKey(),
                            userDictionary.getHashTag(),
                            userDictionary.getHostId(),
                            userDictionary.getHost()
                            ,1
                    ));
                    adapter.notifyItemInserted(adapter.getItemCount()-1);
                mb.dictionaryList.scrollToPosition(adapter.getItemCount() - 1);

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
     * @param dictionary : 단어장 정보 (설명,해시태그,최대개수,공개여부,제목)
     */
    private void createFirebaseReadDatabase(String userId,UserDictionary dictionary) {
        DatabaseReference pushRef = Util.myRefWord.child(userId).push();
        String roomKey = pushRef.getKey();
        dictionary.setRoomKey(roomKey);
        pushRef.setValue(dictionary);
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
                sendDataToActivity.sendDictData(item);
                activity.changeFragment(Const.DICTIONARY_INFO_FRAGMENT);
            }

            // 롱클릭 => 수정,삭제 다이얼로그
            @Override
            public void onLongClick(View v, int pos) {
                DictionaryUpdateDialog dialog = new DictionaryUpdateDialog(getContext(), Const.DIALOG_DICT_WORD);
                dialog.setDialogUpdateCallback(new DialogUpdateCallback() {
                    @Override
                    // 다이얼로그에서 수정버튼을 눌렀을때
                    public void setOnClickUpdateButton() {
                        // TODO : 바텀시트띄워서 정보수정
                        CreateDictionarySheetDialog createDictionarySheetDialog = new CreateDictionarySheetDialog(activity);
                        if (getFragmentManager() != null) {
                            createDictionarySheetDialog.show(getFragmentManager(),"update Dict");
                        }

                    }

                    @Override
                    public void setOnClickDeleteButton() {
                        //removeWordDictList(pos);
                        String roomKey = adapter.getItem(pos).getDictRoomKey();
                        removeWordDictList(roomKey,pos);
                        
                        // TODO : 해당 단어장 삭제후 어댑터 다시 업데이트

                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public void createDialogGetData(UserDictionary userDictionary) {
        createFirebaseReadDatabase(SharedManger.loadData(Const.SHARED_USER_ID,""),userDictionary);
    }
}
