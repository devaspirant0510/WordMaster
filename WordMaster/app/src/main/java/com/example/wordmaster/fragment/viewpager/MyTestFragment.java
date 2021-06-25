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

import com.example.wordmaster.Define.Util;
import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.adapter.OnlineTestAdapter;
import com.example.wordmaster.callback.SendDataToActivity;
import com.example.wordmaster.databinding.FragmentMyTestBinding;
import com.example.wordmaster.dialog.bottomsheet.WordTestSettingDialog;
import com.example.wordmaster.model.firebase.UserTest;
import com.example.wordmaster.model.recycler.OnlineTestItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import org.jetbrains.annotations.NotNull;

public class MyTestFragment extends Fragment {
    private FragmentMyTestBinding mb;
    private OnlineTestAdapter adapter;
    private MainActivity activity;
    private SendDataToActivity listener = null;
    private WordTestSettingDialog dialog;
    private static final String TAG = "MyTestFragment";
    private String spUserId,spUserEmail,spUserName;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
        SharedPreferences sharedPreferences = activity.getSharedPreferences("LoginInformation", Context.MODE_PRIVATE);
        spUserId = sharedPreferences.getString("userId","");
        spUserEmail = sharedPreferences.getString("userEmail","");
        spUserName = sharedPreferences.getString("userNickName","");
        Log.e(TAG, "onAttach: e"+spUserId );
    }

    public void setListener(SendDataToActivity listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentMyTestBinding.inflate(getLayoutInflater());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        readDB();
        return mb.getRoot();


    }


    private void init() {
        adapter = new OnlineTestAdapter(getContext());
        adapter.setOnClickListener(new OnlineTestAdapter.onClickListener() {
            @Override
            public void onClickViewMore(int pos) {

                Log.e(TAG, "onClickViewMore: " );
            }

            @Override
            public void onClickJoin(int pos) {
                Log.e(TAG, "onClickJoin: " );

            }
        });
        mb.fBtnAddTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordTestSettingDialog dialog = new WordTestSettingDialog();
                if (getFragmentManager() != null) {
                    dialog.show(getFragmentManager(),"");
                }
            }
        });
        mb.myTestList.setAdapter(adapter);
    }

    private void readDB() {
        Util.myRefTest.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                UserTest userTest= snapshot.getValue(UserTest.class);
                if (userTest!=null){
                    adapter.addItem(new OnlineTestItem(
                            userTest.getTitle(),
                            userTest.getUserName(),
                            userTest.getUserId(),
                            userTest.getPassword(),
                            "fd",
                            13,
                            userTest.getDescription(),
                            userTest.getStartTime(),
                            userTest.getEndTime(),
                            String.valueOf(userTest.getOption()),
                            3,
                            userTest.getType())
                    );
                    adapter.notifyItemInserted(adapter.getItemCount()-1);
                }


            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

}
