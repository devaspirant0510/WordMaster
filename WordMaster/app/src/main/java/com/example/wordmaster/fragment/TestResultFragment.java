package com.example.wordmaster.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.databinding.FragmentTestResultBinding;

public class TestResultFragment extends Fragment {
    private FragmentTestResultBinding mb;
    private int maxCount,score;
    private String myArr[];
    private String answerArr[];

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Bundle getArgs = getArguments();
        if (getArgs!=null){
            maxCount = getArgs.getInt("testMaxCount");
            score = getArgs.getInt("testCurrentCount");
            myArr = getArgs.getStringArray("myArr");
            answerArr = getArgs.getStringArray("answerArr");

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentTestResultBinding.inflate(getLayoutInflater());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mb.tvTestResult.setText(score+"/"+maxCount);
        return mb.getRoot();

    }
}
