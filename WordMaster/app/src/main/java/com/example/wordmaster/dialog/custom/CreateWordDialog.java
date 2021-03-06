package com.example.wordmaster.dialog.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.adapter.DictionaryInfoAdapter;
import com.example.wordmaster.callback.InfoFragmentDialogCallback;
import com.example.wordmaster.data.recycler.DictionaryWordItem;
import com.example.wordmaster.databinding.DialogCreateWordDialogBinding;
import com.example.wordmaster.define.Define;
import com.example.wordmaster.fragment.DictionaryInfoFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateWordDialog extends Dialog  {
    private DialogCreateWordDialogBinding mb;
    private Context context;
    private DictionaryInfoFragment fragment;
    private DictionaryInfoAdapter adapter;
    private MainActivity activity;
    private String title;
    private static final String TAG = "CreateWordDialog";
    private ArrayList<DictionaryWordItem> wordList;
    public CreateWordDialog(@NonNull Context context,ArrayList<DictionaryWordItem> list,DictionaryInfoAdapter adapter,String tilte) {
        super(context);
        this.context = context;
        this.adapter = adapter;
        this.wordList =list;
        this.title = tilte;
        activity = (MainActivity)getOwnerActivity();
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
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(Define.USER);

                int idx = adapter.getItemCount()+1;
                myRef.child(title).child("list").child(String.valueOf(idx)).setValue(new DictionaryWordItem(kor,eng));
                dismiss();
            }
        });
    }



}
