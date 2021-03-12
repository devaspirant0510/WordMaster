package com.example.wordmaster.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.databinding.FragmentTestBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TestFragment extends Fragment{
    private FragmentTestBinding mb;
    private int dictMaxCount,rgDictType,rgOption;
    private String dictTitle,dictHostName,limitTime;
    private static String TAG = "TestFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentTestBinding.inflate(getLayoutInflater());
        Bundle getArgs = getArguments();
        if (getArgs!=null){
            dictTitle = getArgs.getString("tvTestTitle");
            dictHostName = getArgs.getString("tvTestHost");
            dictMaxCount = getArgs.getInt("testMaxCount");
            limitTime = getArgs.getString("testLimitTime");
            rgDictType = getArgs.getInt("rgTestType");
            rgOption = getArgs.getInt("rgTestTimeOption");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        readDBListItem();
        return mb.getRoot();
    }
    public void readDBListItem(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(dictHostName);
        myRef.child(dictTitle).child("list").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e(TAG, "onDataChange: "+snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void init() {
        mb.tvWordTestProgressText.setText("1/"+dictMaxCount);
        mb.tvTestTitle.setText(dictTitle);
        mb.tvTestHostName.setText(dictHostName+"님의 테스트");

    }
}
