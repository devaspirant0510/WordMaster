package com.example.wordmaster.view.dialog.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.wordmaster.view.activities.MainActivity;
import com.example.wordmaster.adapter.DictionaryInfoAdapter;
import com.example.wordmaster.databinding.DialogCreateWordDialogBinding;
import com.example.wordmaster.view.fragment.DictionaryInfoFragment;
import com.example.wordmaster.model.recycler.DictionaryWordItem;

import java.util.ArrayList;

public class CreateWordDialog extends Dialog  {
    private DialogCreateWordDialogBinding mb;
    private Context context;
    private DictionaryInfoFragment fragment;
    private DictionaryInfoAdapter adapter;
    private MainActivity activity;
    private ArrayList<String> getPosWord = new ArrayList<>();
    private String mode;
    private String title,spUserId;
    private int pos;
    private String getRoomKey;
    private static final String TAG = "CreateWordDialog";
    private ArrayList<DictionaryWordItem> wordList;
    private OnClickListener listener = null;
    private static final int ADD = 0;
    private static final int UPDATE = 1;
    private int mod;
    public interface OnClickListener{
        void onSubmitClick(String eng, String kor,int mode);
    }
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;

    }
    public CreateWordDialog(@NonNull Context context,String spUserId,ArrayList<DictionaryWordItem> list,DictionaryInfoAdapter adapter,String tilte,String mode,String room) {
        super(context);
        mod = ADD;
        this.context = context;
        this.adapter = adapter;
        this.wordList =list;
        this.title = tilte;
        this.spUserId = spUserId;
        this.mode = mode;
        this.getRoomKey = room;
        activity = (MainActivity)getOwnerActivity();
    }

    public void setUpdateWord(String eng,String kor,int pos){
        Log.e(TAG, "setUpdateWord: "+eng );
        Log.e(TAG, "setUpdateWord: "+kor );
        mb.etEng.setText(eng);
        mb.etKor.setText(kor);
        mod = UPDATE;
        this.pos = pos;


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = DialogCreateWordDialogBinding.inflate(getLayoutInflater());
        setContentView(mb.getRoot());


        init();

//        mb.etEng.setText(getPosWord.get(0));
//        mb.etEng.setText(getPosWord.get(1));

    }
    private void init() {
        mb.btnCreateWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eng = mb.etEng.getText().toString();
                String kor = mb.etKor.getText().toString();
                listener.onSubmitClick(eng,kor,mod);
                dismiss();
            }
        });
    }



}
