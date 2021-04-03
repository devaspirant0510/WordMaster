package com.example.wordmaster.dialog.bottomsheet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wordmaster.databinding.DialogBottomSheetSetWordTestBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class WordTestSettingDialog extends BottomSheetDialogFragment {
    private DialogBottomSheetSetWordTestBinding mb;
    private TestBottomSheetCallBack listener;
    private int dictMaxCount;

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

        mb.testStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int testMaxCount = mb.tvTestMaxCount.getProgress();
                String testLimitTime = mb.tvTestLimitTime.getText().toString();
                mb.rgTestType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                    }
                });
                int rgTestType = mb.rgTestType.getCheckedRadioButtonId();
                int rgTestTimeOption = mb.rgTestTimeOption.getCheckedRadioButtonId();
                Toast.makeText(getContext(),testMaxCount+"",Toast.LENGTH_SHORT ).show();
                dismiss();
                listener.setOnClickListener(testMaxCount,testLimitTime,rgTestType,rgTestTimeOption);
            }
        });
        mb.tvTestMaxCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("d","d"+progress);
                mb.tvPrograssCount.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("d","d"+seekBar.getProgress());
                mb.tvPrograssCount.setText(String.valueOf(seekBar.getProgress()));

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("d","d"+seekBar.getProgress());
                mb.tvPrograssCount.setText(String.valueOf(seekBar.getProgress()));

            }
        });
    }
}
