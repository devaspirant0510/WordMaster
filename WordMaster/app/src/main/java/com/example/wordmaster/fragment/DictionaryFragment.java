package com.example.wordmaster.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.adapter.DictionaryListAdapter;
import com.example.wordmaster.callback.BottomSheetCallBack;
import com.example.wordmaster.callback.DialogUpdateCallback;
import com.example.wordmaster.callback.DictionaryFragmentCallBack;
import com.example.wordmaster.callback.DictionaryListCallBack;
import com.example.wordmaster.callback.SendDataToActivity;
import com.example.wordmaster.data.firebase.UserDictionary;
import com.example.wordmaster.data.recycler.DictionaryListItem;
import com.example.wordmaster.databinding.FragmentDictionaryBinding;
import com.example.wordmaster.define.Define;
import com.example.wordmaster.dialog.bottomsheet.CreateDictionarySheetDialog;
import com.example.wordmaster.dialog.custom.DictionaryUpdateDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class DictionaryFragment extends Fragment implements BottomSheetCallBack {
    private FragmentDictionaryBinding mb;
    private static final String TAG = "DictionaryFragment";
    private CreateDictionarySheetDialog dialog;
    private MainActivity activity;
    private DictionaryListAdapter adapter;
    private SendDataToActivity sendDictData = null;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mMyRef;
    private String title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity)getActivity();
        mDatabase = FirebaseDatabase.getInstance();
        mMyRef = mDatabase.getReference();



    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SendDataToActivity){
            sendDictData = (SendDataToActivity) context;

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

    private void readDB() {
        mMyRef.child(Define.USER).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserDictionary userDictionary = snapshot.getValue(UserDictionary.class);
                title = userDictionary.getTitle();
                adapter.addItem(new DictionaryListItem(
                        userDictionary.getTitle(),
                        String.valueOf(userDictionary.getMaxCount()),
                        userDictionary.getDescription(),
                        Define.USER,
                        userDictionary.getOption(),
                        1
                ));
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserDictionary userDictionary = snapshot.getValue(UserDictionary.class);
                title = userDictionary.getTitle();
                adapter.addItem(new DictionaryListItem(
                        userDictionary.getTitle(),
                        String.valueOf(userDictionary.getMaxCount()),
                        userDictionary.getDescription(),
                        userDictionary.getHashTag(),
                        userDictionary.getOption(),
                        1
                ));
                adapter.notifyDataSetChanged();

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


    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: " );
    }

    private void createFirebaseReadDatabase(String id, UserDictionary dictionary) {
        mMyRef.child(Define.USER).child(id).setValue(dictionary);

    }

    private void init() {
        adapter = new DictionaryListAdapter(getContext());
        Log.e(TAG, "init: "+adapter.dictList);
        mb.dictionaryList.setAdapter(adapter);
        //추가버튼 눌렀을때
        mb.createDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 dialog = new CreateDictionarySheetDialog(activity);
                if (getFragmentManager()!=null){
                    dialog.show(getFragmentManager(),"dd");
                }
            }
        });
        // 리사이클러뷰의 아이템을 클릭했을때
        adapter.setDictionaryListCallBack(new DictionaryListCallBack() {
            @Override
            public void onClick(View v, int pos) {
                // 해당 단어장의 세부정보 보여주는 화면으로 전환
                DictionaryListItem item = adapter.getItem(pos);
                Toast.makeText(getContext(),item.getDictionaryTitle(),Toast.LENGTH_SHORT).show();
                sendDictData.sendDictData(item.getDictionaryTitle(),item.getDictOption(),item.getDictionaryDescription(),item.getDictionaryHost(),Integer.parseInt(item.getDictionaryMaxCount()));
                activity.changeFragment(Define.DICTIONARY_INFO_FRAGMENT);
            }

            @Override
            public void onLongClick(View v, int pos) {
                DictionaryUpdateDialog dialog  = new DictionaryUpdateDialog(getContext(),Define.DIALOG_DICT_WORD);
                dialog.setDialogUpdateCallback(new DialogUpdateCallback() {
                    @Override
                    public void setOnClickUpdateButton() {

                    }

                    @Override
                    public void setOnClickDeleteButton() {
                        Toast.makeText(getContext(),pos+"",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public void createDialogGetData(String title, int count, String description, String hashTag,String DictOption) {
        createFirebaseReadDatabase(title,new UserDictionary(DictOption,title,count,description,hashTag));

    }
}
