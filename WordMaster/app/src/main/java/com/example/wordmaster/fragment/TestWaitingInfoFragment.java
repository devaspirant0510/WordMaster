package com.example.wordmaster.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.callback.SendDataToActivity;
import com.example.wordmaster.databinding.FragmentTestWaitingInfoBinding;
import com.example.wordmaster.model.recycler.OnlineTestItem;

import org.jetbrains.annotations.NotNull;

/**
 * @author seungho
 * @since 2021-06-27
 * class TestWaitingInfoFragment.java
 * project WordMaster
 * github devaspirant0510
 * email seungho020510@gmail.com
 * description 온라인 테스트 프레그먼트에서 시험 아이템의 세부사항 눌렀을때 화면
 **/
public class TestWaitingInfoFragment extends Fragment {
    private FragmentTestWaitingInfoBinding mb;
    private SendDataToActivity listener = null;
    private OnlineTestItem item;

    public void setListener(SendDataToActivity listener){
        this.listener=  listener;
    }
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentTestWaitingInfoBinding.inflate(getLayoutInflater());
        Bundle bundle = getArguments();
        if (bundle!=null){
            item = (OnlineTestItem)bundle.getSerializable("item");

            mb.setTestData(item);

        }
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return mb.getRoot();
    }
}
