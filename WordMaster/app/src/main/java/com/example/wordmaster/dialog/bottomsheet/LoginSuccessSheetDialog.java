package com.example.wordmaster.dialog.bottomsheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.wordmaster.databinding.DialogBottomSheetLoginSuccessBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class LoginSuccessSheetDialog extends BottomSheetDialogFragment {
    private DialogBottomSheetLoginSuccessBinding mb;
    private String profile_uri,user_name;
    private BottomSheetSetOnClick bottomSheetSetOnClick = null;

    public interface BottomSheetSetOnClick{
        public void clickSubmit(String name);
    }
    public void setBottomSheetSetOnClick(BottomSheetSetOnClick bottomSheetSetOnClick){
        this.bottomSheetSetOnClick = bottomSheetSetOnClick;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = DialogBottomSheetLoginSuccessBinding.inflate(getLayoutInflater());
        Bundle bundle = getArguments();
        profile_uri = bundle.getString("image_uri");
        user_name = bundle.getString("nickname");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        return mb.getRoot();

    }

    private void init() {
        Glide.with(this).load(profile_uri).into(mb.ivProfileImage);
        mb.etUserName.setText(user_name);
        mb.btnLoginSuccessSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetSetOnClick.clickSubmit(mb.etUserName.getText().toString());

            }
        });
    }
}
