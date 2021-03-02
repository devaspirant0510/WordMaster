package com.example.wordmaster.dialog.bottomsheet;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.callback.BottomSheetCallBack;
import com.example.wordmaster.databinding.DialogBottomSheetCreateDictBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CreateDictionarySheetDialog extends BottomSheetDialogFragment {
    private DialogBottomSheetCreateDictBinding mb;
    private static final String TAG = "CreateDictionarySheetDialog";
    private RadioGroup radioGroup;
    private BottomSheetCallBack bottomSheetCallBack;
    private MainActivity activity;
    public CreateDictionarySheetDialog(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = DialogBottomSheetCreateDictBinding.inflate(getLayoutInflater());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = mb.getRoot();
        init();
        return root;
    }

    private void init() {
        radioGroup = mb.createDictOpenOption;
        mb.createDictSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String createDictTitle = mb.createDictTitle.getText().toString();
                int createDictCount = Integer.parseInt(mb.createDictCount.getText().toString());
                String createDictDescription = mb.createDictDescription.getText().toString();
                String createDictHashTag = mb.createDictTag.getText().toString();
                int groupType = radioGroup.getCheckedRadioButtonId();
                Log.e(TAG,radioGroup.getCheckedRadioButtonId()+"");
                activity.sendCreateDictDialog(createDictTitle,createDictCount,createDictDescription,createDictHashTag,groupType);
                dismiss();
            }
        });
    }

}
