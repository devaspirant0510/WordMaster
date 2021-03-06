package com.example.wordmaster.callback;

import com.example.wordmaster.adapter.DictionaryInfoAdapter;
import com.example.wordmaster.data.recycler.DictionaryWordItem;

import java.util.ArrayList;

public interface InfoFragmentDialogCallback {
    void send(ArrayList<DictionaryWordItem> list, DictionaryInfoAdapter adapter, String title);
}
