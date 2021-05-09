package com.example.wordmaster.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.Define.Util;
import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.adapter.SearchAdapter;
import com.example.wordmaster.databinding.FragmentSearchBinding;
import com.example.wordmaster.dialog.bottomsheet.SearchInfoSheetDialog;
import com.example.wordmaster.model.firebase.UserDictionary;
import com.example.wordmaster.model.recycler.SearchItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding mb;
    private static final String TAG = "SearchFragment";
    private SearchAdapter mAdapter;
    private MainActivity activity;
    private String user,profile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentSearchBinding.inflate(getLayoutInflater());
        activity = (MainActivity)getActivity();

        SharedPreferences preferences = activity.getSharedPreferences("LoginInformation", Context.MODE_PRIVATE);
        user = preferences.getString("UserID","");
        Log.e(TAG, "onCreate: "+user );



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        readFireBaseDB();
        return mb.getRoot();

    }
    private void init(){
        mAdapter = new SearchAdapter();
        mAdapter.setOnCustomOnClickListener(new SearchAdapter.OnClickListener() {
            @Override
            public void onClick(int click, View view) {
                Toast.makeText(getContext(),"gggg"+click,Toast.LENGTH_SHORT).show();
                SearchInfoSheetDialog sheetDialog = new SearchInfoSheetDialog();
                Bundle args = new Bundle();
                args.putString("user",user);
                args.putInt("pos",click);

                sheetDialog.setArguments(args);
                if (getFragmentManager() != null) {
                    sheetDialog.show(getFragmentManager(),"sheet");
                }

            }

            @Override
            public void longClick(int click, View view) {

            }
        });
        mb.wordDictionaryList.setAdapter(mAdapter);
    }
    private void readFireBaseDB(){
        Log.e(TAG, "readFireBaseDB: "+user );
        Util.myRefWord.child("WordStore").child("1687548254").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e(TAG, "onDataChange: "+snapshot.getValue() );
                for (DataSnapshot data:snapshot.getChildren()) {
                    UserDictionary dictionary = data.getValue(UserDictionary.class);
                    int currentCount = dictionary.getCurrentCount();
                    String description = dictionary.getDescription();
                    String hashTag = dictionary.getHashTag();
                    int maxCount = dictionary.getMaxCount();
                    String option = dictionary.getOption();
                    String title = dictionary.getTitle();
                    Log.e(TAG, "onDataChange: "+title );
                    mAdapter.addItem(new SearchItem("승호",title,description,currentCount+"/"+maxCount,3));
                    mb.wordDictionaryList.scrollToPosition(mAdapter.getItemCount()-1);


                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
