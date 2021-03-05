package com.example.wordmaster.dialog.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.wordmaster.adapter.DictionaryInfoAdapter;
import com.example.wordmaster.data.recycler.DictionaryWordItem;
import com.example.wordmaster.databinding.DialogCreateWordDialogBinding;

public class CreateWordDialog extends Dialog {
    private DialogCreateWordDialogBinding mb;
    private DictionaryInfoAdapter adapter;
    public CreateWordDialog(@NonNull Context context, DictionaryInfoAdapter adapter) {
        super(context);
        this.adapter = adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = DialogCreateWordDialogBinding.inflate(getLayoutInflater());
        setContentView(mb.getRoot());
        init();

    }

    private void init() {
        mb.btnCreateWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kor = mb.etKor.getText().toString();
                String eng = mb.etEng.getText().toString();
                //adapter.addItem(new DictionaryWordItem(kor,eng));
                adapter.addItem(new DictionaryWordItem(kor,eng));
                adapter.notifyDataSetChanged();
                dismiss();
            }
        });
    }


}
