package com.example.wordmaster.dialog.bottomsheet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wordmaster.Define.Util;
import com.example.wordmaster.adapter.DictionaryInfoAdapter;
import com.example.wordmaster.databinding.DialogBottomSheetPreviewWordListBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

/**
 * @author seungho
 * @since 2021-06-16
 * project com.example.wordmaster.dialog.bottomsheet
 * class PreviewWordListDialog.java
 * github devaspirant0510
 * email seungho020510@gmail.com
 * description SearchFragment 에서 유저가 올린 단어장 미리보기 기능
 **/
public class PreviewWordListDialog extends BottomSheetDialogFragment {
    private DialogBottomSheetPreviewWordListBinding mb;
    private String userId,roomKey;
    private static String TAG ="PreviewWordListDialog";

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = DialogBottomSheetPreviewWordListBinding.inflate(getLayoutInflater());
        Bundle bundle = getArguments();
        if (bundle!=null){
            userId = bundle.getString("id");
            roomKey = bundle.getString("roomKey");

        }
    }

    private void readWordList(){
        Log.e(TAG, "readWordList: "+userId );
        Log.e(TAG, "readWordList: "+roomKey );
        Util.myRefWord.child(userId).child(roomKey).child("list").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Log.e(TAG, "onDataChange: "+snapshot );

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        DictionaryInfoAdapter adapter = new DictionaryInfoAdapter(getContext());
        mb.rvPreviewList.setAdapter(adapter);
        readWordList();


        return mb.getRoot();
    }
}
