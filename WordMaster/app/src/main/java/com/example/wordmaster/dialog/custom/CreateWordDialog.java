package com.example.wordmaster.dialog.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.adapter.DictionaryInfoAdapter;
import com.example.wordmaster.databinding.DialogCreateWordDialogBinding;
import com.example.wordmaster.fragment.DictionaryInfoFragment;
import com.example.wordmaster.model.recycler.DictionaryWordItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public interface OnClickListener{
        void onSubmitClick(String eng, String kor);
    }
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;

    }
    public CreateWordDialog(@NonNull Context context,String spUserId,ArrayList<DictionaryWordItem> list,DictionaryInfoAdapter adapter,String tilte,String mode,String room) {
        super(context);
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
    public void updateDialog(int pos){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("WordStore");
        myRef.child(spUserId).child(title).child("list").child(String.valueOf(pos)).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e(TAG, "onChildAdded: "+snapshot.getValue() );
                getPosWord.add(String.valueOf(snapshot.getValue()));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e(TAG, "onChildAdded: "+snapshot );

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void init() {
        mb.btnCreateWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eng = mb.etEng.getText().toString();
                String kor = mb.etKor.getText().toString();
                listener.onSubmitClick(eng,kor);
                dismiss();
            }
        });
    }



}
