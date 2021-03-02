package com.example.wordmaster.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.adapter.DictionaryListAdapter;
import com.example.wordmaster.callback.BottomSheetCallBack;
import com.example.wordmaster.data.DictionaryListItem;
import com.example.wordmaster.databinding.FragmentDictionaryBinding;
import com.example.wordmaster.dialog.bottomsheet.CreateDictionarySheetDialog;


public class DictionaryFragment extends Fragment implements BottomSheetCallBack {
    private FragmentDictionaryBinding mb;
    private static final String TAG = "DictionaryFragment";
    private CreateDictionarySheetDialog dialog;
    private MainActivity activity;
    private DictionaryListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity)getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.BottomSheetCallBack(this);
        mb = FragmentDictionaryBinding.inflate(getLayoutInflater());
        init();

        View root = mb.getRoot();
        return root;

    }

    private void init() {
         adapter = new DictionaryListAdapter(getContext());
        Log.e(TAG, "init: "+adapter.dictList);
        mb.dictionaryList.setAdapter(adapter);
        mb.createDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 dialog = new CreateDictionarySheetDialog(activity);

                if (getFragmentManager()!=null){
                    dialog.show(getFragmentManager(),"dd");
                }
            }
        });
    }

    @Override
    public void createDialogGetData(String title, int count, String description, String hashTag, int groupType) {
        Log.e(TAG,title);
        adapter.addItem(new DictionaryListItem(title,String.valueOf(count),description,hashTag,1));
        adapter.notifyDataSetChanged();

    }


}
