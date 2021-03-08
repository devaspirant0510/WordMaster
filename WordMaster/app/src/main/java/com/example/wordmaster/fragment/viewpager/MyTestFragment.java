package com.example.wordmaster.fragment.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.adapter.DictionaryListAdapter;
import com.example.wordmaster.callback.DictionaryListCallBack;
import com.example.wordmaster.data.firebase.UserDictionary;
import com.example.wordmaster.data.recycler.DictionaryListItem;
import com.example.wordmaster.databinding.DialogBottomSheetSetWordTestBinding;
import com.example.wordmaster.databinding.FragmentMyTestBinding;
import com.example.wordmaster.databinding.FragmentTestBinding;
import com.example.wordmaster.define.Define;
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
    private static final String TAG = "MyTestFragment";
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity)getActivity();
        Log.e(TAG, TAG);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentMyTestBinding.inflate(getLayoutInflater());
        init();
        readDB();
    }


    private void init() {
        adapter = new DictionaryListAdapter(getContext());
        mb.myTestList.setAdapter(adapter);
        adapter.setDictionaryListCallBack(new DictionaryListCallBack() {
            @Override
            public void onClick(View v, int pos) {
                WordTestSettingDialog dialog = new WordTestSettingDialog();
                dialog.show(getFragmentManager(),"fr");
            }
        });


    }

    private void readDB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child(Define.USER).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserDictionary userDictionary = snapshot.getValue(UserDictionary.class);
                Log.e(TAG, "onChildAdded: "+userDictionary.getTitle() );
                adapter.addItem(new DictionaryListItem(
                        userDictionary.getTitle(),
                        String.valueOf(userDictionary.getMaxCount()),
                        userDictionary.getDescription(),
                        Define.USER,
                        userDictionary.getOption(),
                        1
                ));
                Log.e(TAG, "onChildAdded: "+adapter.dictList );
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mb.getRoot();


    }
}
