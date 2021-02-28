package com.example.wordmaster.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.adapter.DictionaryListAdapter;
import com.example.wordmaster.data.DictionaryListItem;
import com.example.wordmaster.databinding.FragmentDictionaryBinding;


public class DictionaryFragment extends Fragment {
    private FragmentDictionaryBinding mb;
    private static final String TAG = "DictionaryFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mb = FragmentDictionaryBinding.inflate(getLayoutInflater());
        init();

        View root = mb.getRoot();
        return root;

    }

    private void init() {
        DictionaryListAdapter adapter = new DictionaryListAdapter(getContext());
        adapter.addItem(new DictionaryListItem("s","s","s","s",1));
        adapter.addItem(new DictionaryListItem("s","s","s","s",1));
        Log.e(TAG, "init: "+adapter.dictList);
        mb.dictionaryList.setAdapter(adapter);

    }
}
