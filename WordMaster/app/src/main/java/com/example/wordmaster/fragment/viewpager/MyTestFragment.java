package com.example.wordmaster.fragment.viewpager;

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

import com.example.wordmaster.activities.LoginActivity;
import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.adapter.DictionaryListAdapter;
import com.example.wordmaster.callback.DictionaryListCallBack;
import com.example.wordmaster.callback.SendDataToActivity;
import com.example.wordmaster.data.firebase.UserDictionary;
import com.example.wordmaster.data.recycler.DictionaryListItem;
import com.example.wordmaster.databinding.FragmentMyTestBinding;
import com.example.wordmaster.model.Define;
import com.example.wordmaster.dialog.bottomsheet.WordTestSettingDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyTestFragment extends Fragment {
    private FragmentMyTestBinding mb;
    private DictionaryListAdapter adapter;
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
        adapter = new DictionaryListAdapter(getContext());
        mb.myTestList.setAdapter(adapter);
        adapter.setDictionaryListCallBack(new DictionaryListCallBack() {
            @Override
            public void onClick(View v, int pos) {
                String host = adapter.getItem(pos).getDictionaryHost();
                String title = adapter.getItem(pos).getDictionaryTitle();
                int DictMaxCount = Integer.parseInt(adapter.getItem(pos).getDictionaryMaxCount());
                dialog = new WordTestSettingDialog(DictMaxCount);
                dialog.setListener(new WordTestSettingDialog.TestBottomSheetCallBack() {
                    @Override
                    public void setOnClickListener(int maxCount, String limitTime, int rgTestType, int rgTestTimeOption) {
                        Toast.makeText(getContext(),maxCount+"",Toast.LENGTH_SHORT).show();
                        listener.sendTestingData(maxCount,limitTime,rgTestType,rgTestTimeOption,host,title);
                        activity.changeFragment(Define.TESTING_FRAGMENT);
                    }
                });
                if (getFragmentManager() != null) {
                    dialog.show(getFragmentManager(), "fr");
                }
            }

            @Override
            public void onLongClick(View v, int pos) {

            }
        });


    }

    private void readDB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("WordStore").child(spUserId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserDictionary userDictionary = snapshot.getValue(UserDictionary.class);
                adapter.addItem(new DictionaryListItem(
                        userDictionary.getTitle(),
                        String.valueOf(userDictionary.getMaxCount()),
                        userDictionary.getDescription(),
                        LoginActivity.USER,
                        userDictionary.getOption(),
                        1
                ));
                Log.e(TAG, "onChildAdded: " + adapter.dictList);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserDictionary userDictionary = snapshot.getValue(UserDictionary.class);
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

}
