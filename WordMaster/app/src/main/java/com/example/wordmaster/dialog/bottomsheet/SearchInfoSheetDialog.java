package com.example.wordmaster.dialog.bottomsheet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.wordmaster.databinding.DialogBottomSheetFragmentSearchInfoBinding;

public class SearchInfoSheetDialog extends DialogFragment {
    private DialogBottomSheetFragmentSearchInfoBinding mb;
    private int pos;
    private String user;
    private static final String TAG = "SearchInfoSheetDialog";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = DialogBottomSheetFragmentSearchInfoBinding.inflate(getLayoutInflater());
        Bundle bundle = getArguments();
        if (bundle != null) {
            user = bundle.getString("user");
            pos = bundle.getInt("pos");
        }
        Log.e(TAG, "onCreate: "+user );
        mb.tvSearchInfoHost.setText(user);




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        return mb.getRoot();

    }
    private void init(){
        Glide.with(this).load("https://k.kakaocdn.net/dn/ifxIf/btq4ehKeRw2/jq820wk55NSiLlKAsbYOy0/img_640x640.jpg").into(mb.imageView);


    }
    private void loadFirebaseDB(int pos){
    }
}
