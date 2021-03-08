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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = DialogBottomSheetSetWordTestBinding.inflate(getLayoutInflater());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mb.getRoot();
    }
}
