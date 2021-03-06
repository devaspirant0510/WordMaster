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
import com.example.wordmaster.callback.DictionaryFragmentCallBack;
import com.example.wordmaster.data.firebase.UserDictionary;
import com.example.wordmaster.data.recycler.DictionaryWordItem;
import com.example.wordmaster.databinding.FragmentDictionaryInfoBinding;
import com.example.wordmaster.define.Define;
import com.example.wordmaster.dialog.custom.CreateWordDialog;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: " );
        mb = FragmentDictionaryInfoBinding.inflate(getLayoutInflater());
        activity = (MainActivity)getActivity();
        // 프레그먼트 전환될때 argument 에 정보 들어있는지 확인
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
        Log.e(TAG, "onResume: " );
        readWordList();

    }
    private void readWordList(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(Define.USER);
        reference.child(dictInfoTitle).child("list").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               // HashMap<String,String> item = snapshot.getValue(HashMap.class);
                //DictionaryWordItem item = snapshot.getValue(DictionaryWordItem.class);
                Log.e(TAG, "onChildAdded: "+snapshot.getChildrenCount() );
                String item = snapshot.getValue().toString();
                item = item.replaceAll("[}{]","");
                String[] splitWord = item.split(",");
                String strEng = splitWord[0].split("=")[1];
                String strKor = splitWord[1].split("=")[1];
                Log.e(TAG, "onChildAdded: "+strEng );
                Log.e(TAG, "onChildAdded: "+strKor );
                adapter.addItem(new DictionaryWordItem(strKor,strEng));
                adapter.notifyDataSetChanged();
//
//                Log.e(TAG, "onChildAdded: "+item );
//                Log.e(TAG, "onChildAdded: "+snapshot.getValue() );
//                Log.e(TAG, "onChildAdded: "+ snapshot.getValue().getClass()+"");

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //DictionaryWordItem item = snapshot.getValue(DictionaryWordItem.class);
                //adapter.addItem(item);

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.setDictionaryListCallBack(this);
        View root = mb.getRoot();
        Log.e(TAG, "onCreateView: " );
        init();
        return root;
    }


    private void init() {
        mb.dictInfoTitle.setText(dictInfoTitle);
        mb.dictInfoOption.setText(dictInfoOption);
        adapter = new DictionaryInfoAdapter(getContext());
        mb.wordList.setAdapter(adapter);
        mb.btnDictInfoCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext()!=null){
                    CreateWordDialog dialog = new CreateWordDialog(getContext(),wordList,adapter,dictInfoTitle);
                    dialog.show();
                }
            }
        });

    }

    @Override
    public void sendInfoData(String title, String option, int count) {
        mb.dictInfoTitle.setText(title);
        mb.dictInfoOption.setText(option);
    }
}
