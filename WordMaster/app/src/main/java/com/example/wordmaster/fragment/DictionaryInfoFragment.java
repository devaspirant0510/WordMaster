package com.example.wordmaster.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.adapter.DictionaryInfoAdapter;
import com.example.wordmaster.callback.DictionaryFragmentCallBack;
import com.example.wordmaster.data.firebase.UserDictionary;
import com.example.wordmaster.data.recycler.DictionaryWordItem;
import com.example.wordmaster.databinding.FragmentDictionaryInfoBinding;
import com.example.wordmaster.define.Define;
import com.example.wordmaster.dialog.custom.CreateWordDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DictionaryInfoFragment extends Fragment implements DictionaryFragmentCallBack {
    private MainActivity activity;
    private FragmentDictionaryInfoBinding mb;
    private static String TAG = "DictionaryInfoFragment";
    private String dictInfoTitle,dictInfoOption,dictDescription,dictHashTag;
    private int dictCount;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentDictionaryInfoBinding.inflate(getLayoutInflater());
        activity = (MainActivity)getActivity();
        // 프레그먼트 전환될때 argument 에 정보 들어있는지 확인
        Bundle bundle = getArguments();
        if (bundle!=null){
            dictInfoTitle = bundle.getString("Title");
            dictInfoOption = bundle.getString("Option");
            dictCount = bundle.getInt("Count");
            dictDescription = bundle.getString("Description");
            dictHashTag = bundle.getString("HashTag");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.setDictionaryListCallBack(this);
        View root = mb.getRoot();
        init();
        return root;
    }


    private void init() {
        mb.dictInfoTitle.setText(dictInfoTitle);
        mb.dictInfoOption.setText(dictInfoOption);
        DictionaryInfoAdapter adapter = new DictionaryInfoAdapter(getContext());
        mb.wordList.setAdapter(adapter);
        mb.btnDictInfoCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext()!=null){
                    CreateWordDialog dialog = new CreateWordDialog(getContext(),adapter);
                    dialog.show();

                }

            }
        });

    }

    @Override
    public void sendInfoData(String title, String option, int count) {
        Log.e(TAG, "title" );
        Toast.makeText(getContext(),"title",Toast.LENGTH_SHORT).show();
        mb.dictInfoTitle.setText(title);
        mb.dictInfoOption.setText(option);


    }
}
