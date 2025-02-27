package ngod.project.wordmaster.callback;

import ngod.project.wordmaster.adapter.DictionaryInfoAdapter;
import ngod.project.wordmaster.model.recycler.DictionaryWordItem;

import java.util.ArrayList;

public interface InfoFragmentDialogCallback {
    void send(ArrayList<DictionaryWordItem> list, DictionaryInfoAdapter adapter, String title);
}
