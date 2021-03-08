package com.example.wordmaster.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class TestViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> pagerList = new ArrayList<>();
    public TestViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void addItem(Fragment fragment){
        pagerList.add(fragment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return pagerList.get(position);
    }

    @Override
    public int getCount() {
        return pagerList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "혼자하기";
            case 1:
                return "같이하기";
            default:
                return null;
        }

    }
}
