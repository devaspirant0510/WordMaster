package com.example.wordmaster.dialog.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wordmaster.databinding.DialogBottomSheetSetWordTestBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class WordTestSettingDialog extends BottomSheetDialogFragment {
    private DialogBottomSheetSetWordTestBinding mb;
    private TestBottomSheetCallBack listener;

    public interface TestBottomSheetCallBack{
        void setOnClickListener(int maxCount,String limitTime,int rgTestType,int rgTestTimeOption);

    }
    public void setListener(TestBottomSheetCallBack listener){
        this.listener = listener;
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
        return mb.getRoot();
    }

    private void init() {
        mb.testStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int testMaxCount = Integer.parseInt(mb.tvTestMaxCount.getText().toString());
                String testLimitTime = mb.tvTestLimitTime.getText().toString();
                int rgTestType = mb.rgTestType.getCheckedRadioButtonId();
                int rgTestTimeOption = mb.rgTestTimeOption.getCheckedRadioButtonId();
                dismiss();
                listener.setOnClickListener(testMaxCount,testLimitTime,rgTestType,rgTestTimeOption);
            }
        });
    }
}
