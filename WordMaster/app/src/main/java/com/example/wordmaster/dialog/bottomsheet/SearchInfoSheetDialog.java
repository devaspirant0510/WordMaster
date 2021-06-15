package com.example.wordmaster.dialog.bottomsheet;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.wordmaster.Define.GlideChip;
import com.example.wordmaster.Define.Util;
import com.example.wordmaster.R;
import com.example.wordmaster.databinding.DialogBottomSheetFragmentSearchInfoBinding;
import com.example.wordmaster.model.firebase.UserAccount;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class SearchInfoSheetDialog extends BottomSheetDialogFragment {
    private DialogBottomSheetFragmentSearchInfoBinding mb;
    private int pos;
    private String user,profileURI;
    private static final String TAG = "SearchInfoSheetDialog";
    private String userId,description,host,title,userName;

    public SearchInfoSheetDialog(String userId) {
        this.userId = userId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
        mb = DialogBottomSheetFragmentSearchInfoBinding.inflate(getLayoutInflater());
        Bundle bundle = getArguments();
        if (bundle != null) {
            user = bundle.getString("user");
            pos = bundle.getInt("pos");
            description = bundle.getString("description");
            host = bundle.getString("host");
            title = bundle.getString("title");
            userName = bundle.getString("userName");
        }
        Log.e(TAG, "onCreate: "+user );
        getProfileURI(userId);
        mb.tvSearchInfoHost.setText(host);
        mb.tvSearchInfoDescription.setText(description);
        mb.tvSearchInfoHost.setText(user);
        mb.tvSearchInfoTitle.setText(title);
    }
    private void getProfileURI(String id){
        Util.myRefUser.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserAccount userAccount = snapshot.getValue(UserAccount.class);
                if (userAccount!=null){
                    profileURI= userAccount.getUserProfileUri();
                    Log.e(TAG, "onDataChange: "+profileURI );
                    mb.imageView.setBackground(new ShapeDrawable(new OvalShape()));
                    mb.imageView.setClipToOutline(true);
                    Glide.with(getContext()).load(profileURI).into(mb.imageView);
                    GlideChip chip = new GlideChip(getContext());
                    chip.setIconUrl(profileURI,null);
                    chip.setText(userName);
                    chip.setChipIconVisible(true);
                    mb.rgUserGroup.addView(chip);


                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        return mb.getRoot();

    }
    private void init(){


    }
    private void loadFirebaseDB(int pos){
    }
}
