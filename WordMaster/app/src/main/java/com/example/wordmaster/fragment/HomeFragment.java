package com.example.wordmaster.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.R;
import com.example.wordmaster.adapter.RankingAdapter;
import com.example.wordmaster.data.recycler.RankingItem;
import com.example.wordmaster.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding mb;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mb = FragmentHomeBinding.inflate(getLayoutInflater());
        init();
        return mb.getRoot();


    }

    private void init() {
        RankingAdapter adapter = new RankingAdapter(getContext());
        adapter.addItem(new RankingItem("3.14썬",1,"lsh0510","강력하다",R.drawable.ic_launcher_foreground));
        adapter.addItem(new RankingItem("유클립트",2,"xkuq1234","유클립트 TV 구독과 좋아요",R.drawable.ic_launcher_foreground));
        adapter.addItem(new RankingItem("황홀한 러닝커브",3,"seungho020510","딥러닝 잘해지고 싶어요",R.drawable.ic_launcher_foreground));
        mb.rankingRecyclerView.setAdapter(adapter);

    }
}
