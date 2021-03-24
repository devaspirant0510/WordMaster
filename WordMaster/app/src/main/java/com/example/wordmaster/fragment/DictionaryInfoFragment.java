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
    private static String TAG = "DictionaryInfoFragment";
    private String dictInfoTitle,dictInfoOption,dictDescription,dictHashTag;
    private DictionaryInfoAdapter adapter;
    private ArrayList<DictionaryWordItem> wordList = new ArrayList<>();
    private int dictCount;
    private String setMode = "add";


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
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: "+adapter.wordList );
        Log.e(TAG, "onResume: " );

    }
    private void readWordList(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(Define.USER);
        reference.child(dictInfoTitle).child("list").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               // HashMap<String,String> item = snapshot.getValue(HashMap.class);
                //DictionaryWordItem item = snapshot.getValue(DictionaryWordItem.class);
                if (setMode.equals("add")){
                    Log.e(TAG, "onChildAdded: "+snapshot.getChildrenCount() );
                    String item = snapshot.getValue().toString();
                    item = item.replaceAll("[}{]","");
                    String[] splitWord = item.split(",");
                    String strEng = splitWord[0].split("=")[1];
                    String strKor = splitWord[1].split("=")[1];
                    adapter.addItem(new DictionaryWordItem(strKor,strEng));
                    adapter.notifyDataSetChanged();
                    Log.e(TAG, "onChildChanged: "+adapter.wordList );
                    mb.progressBar.setProgress(adapter.getItemCount());

                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //DictionaryWordItem item = snapshot.getValue(DictionaryWordItem.class);
                //adapter.addItem(item);
//                Log.e(TAG, "onChildAdded: "+snapshot.getChildrenCount() );
//                String item = snapshot.getValue().toString();
//                item = item.replaceAll("[}{]","");
//                String[] splitWord = item.split(",");
//                String strEng = splitWord[0].split("=")[1];
//                String strKor = splitWord[1].split("=")[1];
//                adapter.addItem(new DictionaryWordItem(strKor,strEng));
//                adapter.notifyDataSetChanged();
//                Log.e(TAG, "onChildChanged: "+adapter.wordList );
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
        View root = mb.getRoot();
        init();
        readWordList();
        mb.progressBar.setProgress(adapter.getItemCount());
        Log.e(TAG, "init: "+adapter.wordList.size() );
        return root;
    }

    private void init() {
        mb.progressBar.setMax(dictCount);
        mb.dictInfoTitle.setText(dictInfoTitle);
        mb.dictInfoOption.setText(dictInfoOption);
        DictionaryUpdateDialog dialog = new DictionaryUpdateDialog(getContext(),Define.DIALOG_DICT_WORD);
        adapter.setDictionaryListCallBack(new DictionaryListCallBack() {
            @Override
            public void onClick(View v, int pos) {
            }

            @Override
            public void onLongClick(View v, int pos) {
                dialog.show();
                dialog.setDialogUpdateCallback(new DialogUpdateCallback() {
                    @Override
                    public void setOnClickUpdateButton() {
                    }

                    @Override
                    public void setOnClickDeleteButton() {
                        setMode="delete";
                        deleteFireBaseData(pos);


                    }
                });


            }
        });

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
    public void deleteFireBaseData(int pos){
        ArrayList<DictionaryWordItem> list = adapter.wordList;
        adapter.removeItem(list.get(pos));
        adapter.notifyDataSetChanged();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Define.USER);
        myRef.child(dictInfoTitle).child("list").setValue(null);
        myRef.child(dictInfoTitle).child("list").setValue(adapter.wordList);



    }


    @Override
    public void sendInfoData(String title, String option, int count) {
        mb.dictInfoTitle.setText(title);
        mb.dictInfoOption.setText(option);
    }
}
