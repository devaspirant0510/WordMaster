package com.example.wordmaster.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.R;
import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.adapter.RankingAdapter;
import com.example.wordmaster.data.recycler.RankingItem;
import com.example.wordmaster.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding mb;
    private String spUserName,spUserId,spUserEmail;
    private MainActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity)getActivity();
        SharedPreferences sharedPreferences = activity.getSharedPreferences("LoginInformation", Context.MODE_PRIVATE);
        spUserId = sharedPreferences.getString("userId","");
        spUserEmail = sharedPreferences.getString("userEmail","");
        spUserName = sharedPreferences.getString("userNickName","");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mb = FragmentHomeBinding.inflate(getLayoutInflater());
        init();
        return mb.getRoot();
    }

    private void init() {
        mb.tvWelcomeMessage.setText(spUserName+"님 환영합니다.");
        RankingAdapter adapter = new RankingAdapter(getContext());
        adapter.addItem(new RankingItem("3.14썬",1,"lsh0510","강력하다",R.drawable.ic_launcher_foreground));
        adapter.addItem(new RankingItem("유클립트",2,"xkuq1234","유클립트 TV 구독과 좋아요",R.drawable.ic_launcher_foreground));
        adapter.addItem(new RankingItem("황홀한 러닝커브",3,"seungho020510","딥러닝 잘해지고 싶어요",R.drawable.ic_launcher_foreground));
        mb.rankingRecyclerView.setAdapter(adapter);

    }
}
