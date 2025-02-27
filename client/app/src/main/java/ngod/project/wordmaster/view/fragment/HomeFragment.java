package ngod.project.wordmaster.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import ngod.project.wordmaster.Define.Const;
import ngod.project.wordmaster.Define.SharedManger;
import ngod.project.wordmaster.Define.Util;
import ngod.project.wordmaster.adapter.DictionaryListAdapter;
import ngod.project.wordmaster.adapter.RankingAdapter;
import ngod.project.wordmaster.callback.DictionaryListCallBack;
import ngod.project.wordmaster.databinding.FragmentHomeBinding;
import ngod.project.wordmaster.model.firebase.UserAccount;
import ngod.project.wordmaster.model.firebase.UserDictionary;
import ngod.project.wordmaster.model.recycler.DictionaryListItem;
import ngod.project.wordmaster.model.recycler.RankingItem;
import ngod.project.wordmaster.view.activities.MainActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private final String TAG = "HomeFragment";
    private FragmentHomeBinding mb;
    private String spUserName, spUserId, spUserEmail;
    private MainActivity activity;
    private Toolbar toolbar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        spUserId = SharedManger.getUserName();
        spUserEmail = SharedManger.getUserEmail();
        spUserName = SharedManger.getUserName();

    }

    private void toolbarSetting() {


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

    private void readDB(RankingAdapter adapter) {
        Util.myRefUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    Log.e(TAG, "onDataChange: " + item);
                    UserAccount account = item.getValue(UserAccount.class);
                    if (account != null) {
                        Log.e(TAG, "onDataChange: " + account.getUserDownloadDict());
                        adapter.addItem(new RankingItem(
                                account.getUserName(),
                                adapter.getItemCount(),
                                "",
                                "",
                                account.getUserProfileUri()
                        ));
                        adapter.notifyItemInserted(adapter.getItemCount() - 1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void init() {
        mb.tvWelcomeMessage.setText(SharedManger.loadData(Const.SHARED_USER_NAME, "") + "님 환영합니다.");
        RankingAdapter adapter = new RankingAdapter(getContext());
        mb.rankingRecyclerView.setAdapter(adapter);
        readDB(adapter);
        DatabaseReference child = Util.myRefWord.child(SharedManger.loadData(Const.SHARED_USER_ID, ""));
        DictionaryListAdapter dictionaryListAdapter = new DictionaryListAdapter(getContext());
        dictionaryListAdapter.setDictionaryListCallBack(new DictionaryListCallBack(){

            @Override
            public void onClick(View v, int pos) {

            }

            @Override
            public void onLongClick(View v, int pos) {

            }
        });
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<UserDictionary> list = new ArrayList<>();
                int count = 0;
                for (DataSnapshot item : snapshot.getChildren()) {
                    if (count < 2) {

                        UserDictionary value = item.getValue(UserDictionary.class);
                        DictionaryListItem dictionaryListItem = new DictionaryListItem(
                                value.getTitle(),
                                String.valueOf(value.getMaxCount()),
                                value.getDescription(),
                                value.getHost(),
                                value.getOption(),
                                value.getRoomKey(),
                                value.getHashTag(),
                                SharedManger.loadData(Const.SHARED_USER_ID, ""),
                                SharedManger.loadData(Const.SHARED_USER_NAME, ""),
                                1,
                                value.getCurrentCount()

                                );
                        dictionaryListAdapter.addItem(dictionaryListItem);
                        list.add(item.getValue(UserDictionary.class));
                        count++;
                    }
                }
                if(count==0){
                    mb.recyclerView2.setVisibility(View.GONE);
                    mb.tvComment.setVisibility(View.VISIBLE);

                }else{
                    mb.recyclerView2.setVisibility(View.VISIBLE);
                    mb.tvComment.setVisibility(View.GONE);
                    mb.recyclerView2.setAdapter(dictionaryListAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
