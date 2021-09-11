package com.example.wordmaster.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.SharedManger;
import com.example.wordmaster.Define.Util;
import com.example.wordmaster.adapter.RankingAdapter;
import com.example.wordmaster.databinding.FragmentHomeBinding;
import com.example.wordmaster.model.firebase.UserAccount;
import com.example.wordmaster.model.recycler.RankingItem;
import com.example.wordmaster.view.activities.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {
    private final String TAG = "HomeFragment";
    private FragmentHomeBinding mb;
    private String spUserName,spUserId,spUserEmail;
    private MainActivity activity;
    private Toolbar toolbar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity)getActivity();
        spUserId = SharedManger.getUserName();
        spUserEmail = SharedManger.getUserEmail();
        spUserName = SharedManger.getUserName();

    }
    private void toolbarSetting(){



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mb = FragmentHomeBinding.inflate(getLayoutInflater());
        return mb.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbarSetting();
        init();

    }

    private void readDB(RankingAdapter adapter){
        Util.myRefUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()) {
                    Log.e(TAG, "onDataChange: "+item);
                    UserAccount account = item.getValue(UserAccount.class);
                    if (account!=null){
                        Log.e(TAG, "onDataChange: "+account.getActivityHistory());
                        adapter.addItem(new RankingItem(
                                account.getUserName(),
                                adapter.getItemCount(),
                                "",
                                "1등한다.",
                                account.getUserProfileUri()
                        ));
                        adapter.notifyItemInserted(adapter.getItemCount()-1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
    private void init() {
        mb.tvWelcomeMessage.setText(SharedManger.loadData(Const.SHARED_USER_NAME,"") +"님 환영합니다.");
        RankingAdapter adapter = new RankingAdapter(getContext());
        mb.rankingRecyclerView.setAdapter(adapter);
        readDB(adapter);
    }
}
