package com.example.wordmaster.dialog.bottomsheet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wordmaster.R;
import com.example.wordmaster.databinding.DialogBottomSheetSetWordTestBinding;
import com.example.wordmaster.Define.Define;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class WordTestSettingDialog extends BottomSheetDialogFragment {
    private DialogBottomSheetSetWordTestBinding mb;
    private TestBottomSheetCallBack listener;
    private int dictMaxCount;
    private int rgTestType = 0;

    public interface TestBottomSheetCallBack{
        void setOnClickListener(int maxCount,String limitTime,int rgTestType,int rgTestTimeOption);

    }
    public void setListener(TestBottomSheetCallBack listener){
        this.listener = listener;
    }

    public WordTestSettingDialog(int dictMaxCount) {
        this.dictMaxCount = dictMaxCount;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = DialogBottomSheetSetWordTestBinding.inflate(getLayoutInflater());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        mb.tvTestMaxCount.setMax(dictMaxCount);//Integer.parseInt(mb.tvTestMaxCount.getText().toString());
        Log.e("asdf","sdf"+mb.tvTestMaxCount.getProgress());
        return mb.getRoot();
    }

    private void init() {

        mb.rgTestType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId==R.id.kor2eng){
                        rgTestType = Define.KOR2ENG;
                    }
                    else if (checkedId == R.id.eng2kor){
                        rgTestType = Define.ENG2KOR;
                    }
                    else{
                        rgTestType = Define.RANDOM;
                    }
                Log.e("sd", "onCheckedChanged: "+rgTestType );
            }
        });
        mb.testStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int testMaxCount = mb.tvTestMaxCount.getProgress();
                String testLimitTime = mb.tvTestLimitTime.getText().toString();
                int rgTestTimeOption = mb.rgTestTimeOption.getCheckedRadioButtonId();
                dismiss();
                listener.setOnClickListener(testMaxCount,testLimitTime,rgTestType,rgTestTimeOption);
            }
        });
        mb.tvTestMaxCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mb.tvPrograssCount.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mb.tvPrograssCount.setText(String.valueOf(seekBar.getProgress()));

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mb.tvPrograssCount.setText(String.valueOf(seekBar.getProgress()));

            }
        });
    }
}
