package com.example.wordmaster.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.databinding.FragmentMyInfoBinding;

public class MyInfoFragment extends Fragment {
    private FragmentMyInfoBinding mb;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mb = FragmentMyInfoBinding.inflate(getLayoutInflater());
        View view = mb.getRoot();
        return view;
    }
}
