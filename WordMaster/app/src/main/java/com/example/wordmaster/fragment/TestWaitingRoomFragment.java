package com.example.wordmaster.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.Define.Util;
import com.example.wordmaster.adapter.DictionaryInfoAdapter;
import com.example.wordmaster.databinding.FragmentTestWaitingRoomBinding;
import com.example.wordmaster.model.recycler.DictionaryWordItem;
import com.example.wordmaster.model.recycler.OnlineTestItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

/**
 * @author seungho
 * @since 2021-06-23
 * class TestWaitingRoom.java
 * project WordMaster
 * github devaspirant0510
 * email seungho020510@gmail.com
 * description 온라인 테스트 대기방
 **/
public class TestWaitingRoomFragment extends Fragment {
    private static String TAG = "TestWaitingRoomFragment";
    private FragmentTestWaitingRoomBinding mb;
    private DictionaryInfoAdapter adapter;
    private String roomKey,userId;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new DictionaryInfoAdapter(getContext(),true);
        mb.rvTestWaitingDictPreview.setAdapter(adapter);
        readDB();


    }
    private void readDB(){
        Util.myRefWord.child(userId).child(roomKey).child("list").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Log.e(TAG, "onDataChange: "+snapshot.getValue() );
                for (DataSnapshot item :snapshot.getChildren() ) {
                    DictionaryWordItem getItem = item.getValue(DictionaryWordItem.class);
                    if (getItem!=null){
                        DictionaryWordItem wordItem = new DictionaryWordItem(
                                getItem.getKor(),
                                getItem.getEng()
                        );
                        wordItem.setIndex(adapter.getItemCount()+1);
                        adapter.addItem(wordItem);
                        adapter.notifyItemInserted(adapter.getItemCount()-1);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentTestWaitingRoomBinding.inflate(getLayoutInflater());
        Bundle bundle = getArguments();
        if (bundle!=null){
            OnlineTestItem item = (OnlineTestItem)bundle.getSerializable("item");
            Log.e(TAG, "onCreate: "+item.getTitle() );
            roomKey = item.getRoomKey();
            userId = item.getHostId();
            mb.tvTestRoomTitle.setText(item.getTitle());
            mb.tvTimeLeft.setText(item.getEndTime());

        }
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {


        return mb.getRoot();
    }
}
