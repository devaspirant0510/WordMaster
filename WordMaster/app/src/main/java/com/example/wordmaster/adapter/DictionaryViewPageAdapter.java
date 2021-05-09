package com.example.wordmaster.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class DictionaryViewPageAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> pager = new ArrayList<>();

    public DictionaryViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return pager.get(position);
    }
    public void addItem(Fragment fragment){
        pager.add(fragment);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "내 단어장";
            case 1:
                return "외부 단어장";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pager.size();
    }
}
