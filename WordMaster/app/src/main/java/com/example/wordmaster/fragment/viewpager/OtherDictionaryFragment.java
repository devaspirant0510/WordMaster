package com.example.wordmaster.fragment.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.SharedManger;
import com.example.wordmaster.Define.Util;
import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.adapter.DictionaryListAdapter;
import com.example.wordmaster.callback.DictionaryListCallBack;
import com.example.wordmaster.callback.SendDataToActivity;
import com.example.wordmaster.databinding.FragmentOtherDictionaryBinding;
import com.example.wordmaster.model.firebase.UserDictionary;
import com.example.wordmaster.model.recycler.DictionaryListItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class OtherDictionaryFragment extends Fragment {
    private static final String TAG = "OtherDictionaryFragment";
    private FragmentOtherDictionaryBinding mb;
    private MainActivity activity;
    private DictionaryListAdapter adapter;
    private SendDataToActivity callback = null;


    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
        if (context instanceof SendDataToActivity) {
            callback = (SendDataToActivity) context;
        }
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentOtherDictionaryBinding.inflate(getLayoutInflater());
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        init();
        readOtherWordList();


        return mb.getRoot();
    }

    private void init() {
        adapter = new DictionaryListAdapter(getContext(), false);
        adapter.setDictionaryListCallBack(new DictionaryListCallBack() {
            @Override
            public void onClick(View v, int pos) {
                DictionaryListItem item = adapter.getItem(pos);


                callback.otherDict2Info(item.getDictionaryTitle(),
                        item.getDictOption(),
                        Integer.parseInt(item.getDictionaryMaxCount()),
                        item.getDictRoomKey(),
                        item.getUserId(),
                        item.getDictionaryHost());
                activity.changeFragment(Const.OTHER_DICT2DICTIONARY_INFO);

                Log.e(TAG, "onClick:" + pos);

            }

            @Override
            public void onLongClick(View v, int pos) {
                Log.e(TAG, "onLongClick: " + pos);

            }
        });
        mb.otherDictList.setAdapter(adapter);
    }

    private void readOtherWordList() {
        String userId = SharedManger.loadData(Const.SHARED_USER_ID, "");
        Util.myRefUser.child(userId).child("userDownloadDict").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Log.e(TAG, "onDataChange:" + snapshot);
                HashMap<String, ArrayList<String>> hashMap = (HashMap<String, ArrayList<String>>) snapshot.getValue();
                if (hashMap != null) {
                    Log.e(TAG, "onDataChange: " + hashMap.keySet());

                    for (String key : hashMap.keySet()) {
                        for (String value : hashMap.get(key)) {
                            Util.myRefWord.child(key).child(value).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    UserDictionary item = snapshot.getValue(UserDictionary.class);
                                    if (item != null) {
                                        adapter.addItem(new DictionaryListItem(
                                                item.getTitle(),
                                                String.valueOf(item.getMaxCount()),
                                                item.getDescription(),
                                                item.getHost(),
                                                item.getOption(),
                                                item.getRoomKey(),
                                                item.getHashTag(),
                                                item.getHostId(),
                                                item.getHost(),
                                                1

                                        ));
                                        adapter.notifyItemInserted(adapter.getItemCount() - 1);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });
                            Log.e(TAG, "onDataChange: " + key + " " + value);

                        }

                    }


                    Log.e(TAG, "onDataChange: " + hashMap.values());
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}
