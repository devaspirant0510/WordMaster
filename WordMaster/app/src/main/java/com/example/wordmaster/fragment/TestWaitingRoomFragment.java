package com.example.wordmaster.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.databinding.FragmentTestWaitingRoomBinding;

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

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {


        return mb.getRoot();
    }
}
