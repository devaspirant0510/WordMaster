package com.example.wordmaster.fragment;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.GlideChip;
import com.example.wordmaster.Define.SharedManger;
import com.example.wordmaster.Define.Util;
import com.example.wordmaster.databinding.FragmentSearchInfoBinding;
import com.example.wordmaster.dialog.bottomsheet.PreviewWordListDialog;
import com.example.wordmaster.model.firebase.UserAccount;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchInfoFragment extends Fragment {
    private FragmentSearchInfoBinding mb;
    private int pos;
    private String user, profileURI;
    private static final String TAG = "SearchInfoSheetDialog";
    private String userId, description, host, title, userName, roomKey;
    private ArrayList<String> hashTag = new ArrayList<>();

    public SearchInfoFragment(String userId) {
        this.userId = userId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentSearchInfoBinding.inflate(getLayoutInflater());
        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getString("id");
            roomKey = bundle.getString("roomKey");
            user = bundle.getString("user");
            pos = bundle.getInt("pos");
            description = bundle.getString("description");
            host = bundle.getString("host");
            title = bundle.getString("title");
            userName = bundle.getString("userName");
            hashTag = bundle.getStringArrayList("hashTag");
            Log.e(TAG, "onCreate: " + hashTag);
        }
        Log.e(TAG, "onCreate: " + user);
        getProfileURI(userId);
        mb.tvSearchInfoHost.setText(host);
        mb.tvSearchInfoDescription.setText(description);
        mb.tvSearchInfoHost.setText(user);
        mb.tvSearchInfoTitle.setText(title);

        mb.btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreviewWordListDialog dialog = new PreviewWordListDialog();
                Bundle args = new Bundle();
                Log.e(TAG, "onClick: " + roomKey);
                args.putString("id", userId);
                args.putString("roomKey", roomKey);
                dialog.setArguments(args);
                if (getFragmentManager() != null) {
                    dialog.show(getFragmentManager(), "");
                }

            }
        });
    }

    private void getProfileURI(String id) {
        Util.myRefUser.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserAccount userAccount = snapshot.getValue(UserAccount.class);
                if (userAccount != null) {
                    profileURI = userAccount.getUserProfileUri();
                    Log.e(TAG, "onDataChange: " + profileURI);
                    mb.imageView.setBackground(new ShapeDrawable(new OvalShape()));
                    mb.imageView.setClipToOutline(true);
                    Glide.with(getContext()).load(profileURI).into(mb.imageView);
                    GlideChip chip = new GlideChip(getContext());
                    chip.setIconUrl(profileURI, null);
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

    private void init() {
        // 다운로드 버튼을 눌렀을때
        mb.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.myRefUser.child(SharedManger.loadData(Const.SHARED_USER_ID, ""))
                        .child("userDownloadDict").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.getValue() == null) {
                            ArrayList<String> firstList = new ArrayList<>();
                            firstList.add(roomKey);
                            Util.myRefUser.child(SharedManger.loadData(Const.SHARED_USER_ID, ""))
                                    .child("userDownloadDict").child(userId).setValue(firstList);

                        } else {
                            ArrayList<String> getList = (ArrayList<String>)snapshot.getValue();
                            if(getList.contains(roomKey)){
                                Toast.makeText(getContext(), "이미 다운로드한 단어장입니다.", Toast.LENGTH_SHORT).show();


                            }else{

                                getList.add(roomKey);
                                Util.myRefUser.child(SharedManger.loadData(Const.SHARED_USER_ID, ""))
                                        .child("userDownloadDict").child(userId).setValue(getList);
                            }
                            Log.e(TAG, "onDataChange: e"+snapshot.getValue() );

                        }
                    }

                    //                        ArrayList<String> list = (ArrayList<String>) snapshot.getValue();
//                        if (list != null) {
//                            if (list.size() != 0 && list.contains(roomKey)) {
//                            } else {
//                                Util.myRefUser.child(SharedManger.loadData(Const.SHARED_USER_ID, ""))
//                                        .child("userDownloadDict").child(userId).child(String.valueOf(list.size())).setValue(roomKey);
//
//                            }
//                        }
//                        }
//
//
//                    }
//
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
//
//
            }
        });

        for (String item : hashTag) {
            Chip chip = new Chip(getContext());
            chip.setText(item);
            mb.cgHashTag.addView(chip);

        }


    }

    private void loadFirebaseDB(int pos) {
    }
}
