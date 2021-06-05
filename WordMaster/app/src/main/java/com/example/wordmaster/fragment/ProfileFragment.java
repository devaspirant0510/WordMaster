package com.example.wordmaster.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.SharedManger;
import com.example.wordmaster.databinding.FragmentProfileBinding;

import org.jetbrains.annotations.NotNull;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding mb;
    private String profileURI;
    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentProfileBinding.inflate(getLayoutInflater());
        profileURI = SharedManger.loadData(Const.SHARED_USER_PROFILE_URI,"");
        Glide.with(getContext()).load(profileURI).into(mb.ivProfile);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return mb.getRoot();

    }
}
