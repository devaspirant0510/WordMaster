package com.example.wordmaster.view.dialog.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wordmaster.databinding.DialogBottomSheetLoginBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class LoginBottomSheetDialog extends BottomSheetDialogFragment {
    private DialogBottomSheetLoginBinding mb;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mb = DialogBottomSheetLoginBinding.inflate(getLayoutInflater());
        View root = mb.getRoot();
        return root;
    }
}
