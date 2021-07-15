package com.example.wordmaster.view.fragment.viewpager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.SharedManger;
import com.example.wordmaster.Define.Util;
import com.example.wordmaster.view.activities.MainActivity;
import com.example.wordmaster.adapter.OnlineTestAdapter;
import com.example.wordmaster.callback.SendDataToActivity;
import com.example.wordmaster.databinding.FragmentMyTestBinding;
import com.example.wordmaster.view.dialog.bottomsheet.WordTestSettingDialog;
import com.example.wordmaster.model.firebase.UserTest;
import com.example.wordmaster.model.recycler.OnlineTestItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import org.jetbrains.annotations.NotNull;

public class MyTestFragment extends Fragment {
    private FragmentMyTestBinding mb;
    private OnlineTestAdapter adapter;
    private MainActivity activity;
    private SendDataToActivity listener = null;
    private WordTestSettingDialog dialog;
    private static final String TAG = "MyTestFragment";
    private String spUserId,spUserEmail,spUserName;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
        spUserId = SharedManger.loadData(Const.SHARED_USER_ID,"");
        spUserEmail = SharedManger.loadData(Const.SHARED_USER_EMAIL,"");
        spUserName = SharedManger.loadData(Const.SHARED_USER_NAME,"");

        Log.e(TAG, "onAttach: e"+spUserId );
    }

    public void setListener(SendDataToActivity listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentMyTestBinding.inflate(getLayoutInflater());

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        readDB();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mb.getRoot();


    }
    private void joinDialog(OnlineTestItem item){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("참여하시겠습니까?");
        dialog.setPositiveButton("참여", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onlineTest2testJoin(item);
                activity.changeFragment(Const.TEST_WAITING_ROOM);
                Toast.makeText(getContext(),"테스트에 참가하였습니다.", Toast.LENGTH_SHORT).show();


            }
        });
        dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void init() {
        adapter = new OnlineTestAdapter(getContext());
        adapter.setOnClickListener(new OnlineTestAdapter.onClickListener() {
            @Override
            public void onClickViewMore(int pos) {
                OnlineTestItem item = adapter.getItem(pos);
                Log.e(TAG, "onClickViewMore: "+item.getTestRoomKey() );
                listener.onlineTest2testInfo(item);
                activity.changeFragment(Const.TEST_WAITING_INFO);

            }

            @Override
            public void onClickJoin(int pos) {
                Log.e(TAG, "onClickJoin: "+pos );
                OnlineTestItem item = adapter.getItem(pos);
                joinDialog(item);


            }
        });
        mb.fBtnAddTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordTestSettingDialog dialog = new WordTestSettingDialog();
                if (getFragmentManager() != null) {
                    dialog.show(getFragmentManager(),"");
                }
            }
        });
        mb.myTestList.setAdapter(adapter);
    }

    private void readDB() {
        Util.myRefTest.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                UserTest userTest= snapshot.getValue(UserTest.class);
                if (userTest!=null){
                    Log.e(TAG, "onChildAdded: "+userTest.getTestRoomKey() );
                    adapter.addItem(new OnlineTestItem(
                            userTest.getTitle(),
                            userTest.getUserName(),
                            userTest.getUserId(),
                            userTest.getRoomKey(),
                            userTest.getPassword(),
                            "fd",
                            13,
                            userTest.getDescription(),
                            userTest.getStartTime(),
                            userTest.getEndTime(),
                            String.valueOf(userTest.getOption()),
                            userTest.getUserCount(),
                            userTest.getTestRoomKey(),
                            userTest.getType())
                    );
                    Log.e(TAG,""+adapter.getItem(0).getTestRoomKey());
                    adapter.notifyItemInserted(adapter.getItemCount()-1);
                }


            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

}
