package com.example.wordmaster.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.SharedManger;
import com.example.wordmaster.Define.Util;
import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.adapter.SearchAdapter;
import com.example.wordmaster.callback.SendDataToActivity;
import com.example.wordmaster.databinding.FragmentSearchBinding;
import com.example.wordmaster.model.firebase.UserDictionary;
import com.example.wordmaster.model.recycler.SearchItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding mb;
    private static final String TAG = "SearchFragment";
    private SearchAdapter mAdapter;
    private MainActivity activity;
    private String spUserId,spUserName,profileURI;
    private SendDataToActivity sendDataToActivity = null;

    public void setOnSendToActivityListener(SendDataToActivity sendToActivityListener){
        this.sendDataToActivity = sendToActivityListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentSearchBinding.inflate(getLayoutInflater());
        activity = (MainActivity) getActivity();
        spUserId = SharedManger.loadData(Const.SHARED_USER_ID,"");
        spUserName = SharedManger.loadData(Const.SHARED_USER_NAME,"");
        init();
        readFireBaseDB();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mb.getRoot();

    }

    public void passwordDialog(SearchItem item){
        EditText editText = new EditText(getContext());
        editText.setHint("비밀번호를 입력하세요");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(item.getTitle());
        builder.setMessage(item.getHost());
        builder.setView(editText);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputPassword = editText.getText().toString();
                if (inputPassword.equals(item.getPassWord())){
                    showInfoDialog(item);


                }else{
                    Toast.makeText(getContext(),"비밀번호가 틀립니다.",Toast.LENGTH_SHORT).show();

                }
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


            }
        });
        builder.show();
    }
    private void showInfoDialog(SearchItem item){
        Bundle args = new Bundle();
        args.putString("id",item.getId());
        args.putString("description",item.getDescription());
        args.putString("host",item.getHost());
        args.putString("title",item.getTitle());
        args.putString("userName",item.getHost());
        args.putString("roomKey",item.getRoomKey());
        Log.e(TAG, "showInfoDialog: "+item.getRoomKey() );
        sendDataToActivity.sendSearchInfoData(args);
        activity.changeFragment(Const.SEARCH_INFO_FRAGMENT);



    }

    private void init() {
        mAdapter = new SearchAdapter();

        mAdapter.setOnCustomOnClickListener(new SearchAdapter.OnClickListener() {
            @Override
            public void onClick(int click, View view,int state) {
                if (state==Const.SEARCH_PUBLIC){
                    showInfoDialog(mAdapter.getItem(click));

                }else{
                    SearchItem item = mAdapter.getItem(click);
                    passwordDialog(item);

                }

            }

            @Override
            public void longClick(int click, View view,int state) {

            }
        });
        mb.wordDictionaryList.setAdapter(mAdapter);
    }

    private void readFireBaseDB() {
        Util.myRefWord.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot userDict : snapshot.getChildren()) {
                    if (userDict.getKey() != null && !userDict.getKey().equals(spUserId)) {
                        Util.myRefWord.child(userDict.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for(DataSnapshot dict:snapshot.getChildren()){

                                    UserDictionary getItem = dict.getValue(UserDictionary.class);
                                    if (getItem!=null){
                                        Log.e(TAG, "onDataChange: "+userDict.getKey() );

                                        mAdapter.addItem(new SearchItem(
                                                getItem.getHost(),
                                                getItem.getTitle(),
                                                getItem.getDescription(),
                                                String.valueOf(getItem.getMaxCount()),
                                                getItem.getPassword(),
                                                userDict.getKey(),
                                                getItem.getRoomKey(),
                                                getItem.getPassword().equals("") ?Const.SEARCH_PUBLIC:Const.SEARCH_PRIVATE
                                        ));
                                        mAdapter.notifyItemInserted(mAdapter.getItemCount()-1);

                                    }

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            }
                        });
                        Log.e(TAG, "onDataChange: " + userDict);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}
