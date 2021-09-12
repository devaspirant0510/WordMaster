package com.example.wordmaster.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.Define.Const;
import com.example.wordmaster.adapter.TestResultAdapter;
import com.example.wordmaster.model.recycler.DictionaryWordItem;
import com.example.wordmaster.model.recycler.TestResultItem;
import com.example.wordmaster.databinding.FragmentTestResultBinding;
import com.example.wordmaster.view.activities.MainActivity;
import com.example.wordmaster.view.dialog.custom.ActivityHistoryDialog;

import java.util.ArrayList;

public class TestResultFragment extends Fragment {
    private FragmentTestResultBinding mb;
    private int maxCount,score;
    private MainActivity mActivity;
    private String myArr[];
    private String answerArr[];
    private ArrayList<DictionaryWordItem> list;
    private static String TAG = "TestResultFragment";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Bundle getArgs = getArguments();
        mActivity = (MainActivity) getActivity();
        if (getArgs!=null){
            maxCount = getArgs.getInt("testMaxCount");
            score = getArgs.getInt("testCurrentCount");
            myArr = getArgs.getStringArray("myArr");
            answerArr = getArgs.getStringArray("answerArr");
            Log.e(TAG, "onAttach:"+maxCount );
            list = (ArrayList<DictionaryWordItem>) getArgs.getSerializable("list");
            Log.e(TAG, "onAttach: "+list.get(0) );
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentTestResultBinding.inflate(getLayoutInflater());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mb.tvTestResult.setText(score+"/"+maxCount);
        init();
        activityHistoryStatusDialog();
        return mb.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        event();
    }
    private void activityHistoryStatusDialog(){
        if(getContext()!=null){
            ActivityHistoryDialog dialog = new ActivityHistoryDialog(getContext());
            dialog.show();
        }
    }
    private void event(){
        mb.btnResultConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.changeFragment(Const.DICTIONARY_FRAGMENT);


            }
        });
    }

    private void init() {
        TestResultAdapter adapter = new TestResultAdapter();
        for (int i=0; i<answerArr.length; i++){
            String isCorrect = myArr[i].equals(answerArr[i]) ?"o":"x";
            adapter.addItem(new TestResultItem(i,list.get(i).getEng(),list.get(i).getKor(),myArr[i],isCorrect));

        }
        mb.wordTestResultList.setAdapter(adapter);
    }
}
