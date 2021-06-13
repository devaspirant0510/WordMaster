package com.example.wordmaster.fragment;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.SharedManger;
import com.example.wordmaster.adapter.ActivityBoardAdapter;
import com.example.wordmaster.databinding.FragmentProfileBinding;

import org.jetbrains.annotations.NotNull;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding mb;
    private String profileURI;
    private ActivityBoardAdapter adapter;
    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentProfileBinding.inflate(getLayoutInflater());
        profileURI = SharedManger.loadData(Const.SHARED_USER_PROFILE_URI,"");
        mb.ivProfile.setBackground(new ShapeDrawable(new OvalShape()));
        mb.ivProfile.setClipToOutline(true);

        Glide.with(getContext()).load(profileURI).into(mb.ivProfile);
        init();
    }

    private void init(){
        GridLayoutManager gm = new GridLayoutManager(getContext(),7, RecyclerView.VERTICAL,false);
        mb.gridRvActivityBoard.setLayoutManager(gm);
        adapter = new ActivityBoardAdapter();
        adapter.addItem(0);
        adapter.addItem(3);
        adapter.addItem(0);
        adapter.addItem(3);
        adapter.addItem(0);
        adapter.addItem(3);
        adapter.addItem(0);
        adapter.addItem(3);
        mb.gridRvActivityBoard.setAdapter(adapter);


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return mb.getRoot();

    }
}
