package com.example.wordmaster.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.Util;
import com.example.wordmaster.view.activities.MainActivity;
import com.example.wordmaster.adapter.DictionaryInfoAdapter;
import com.example.wordmaster.callback.SendDataToActivity;
import com.example.wordmaster.databinding.FragmentTestWaitingRoomBinding;
import com.example.wordmaster.model.recycler.DictionaryWordItem;
import com.example.wordmaster.model.recycler.OnlineTestItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author seungho
 * @since 2021-06-23
 * class TestWaitingRoom.java
 * project WordMaster
 * github devaspirant0510
 * email seungho020510@gmail.com
 * description 온라인 테스트 대기방
 **/
public class TestWaitingRoomFragment extends Fragment {
    private static String TAG = "TestWaitingRoomFragment";
    private FragmentTestWaitingRoomBinding mb;
    private DictionaryInfoAdapter adapter;
    private String roomKey, userId;
    private MainActivity activity;
    private OnlineTestItem item;
    private SendDataToActivity listener = null;
    private TimeHandler handler = new TimeHandler();
    private boolean testAvaliable = false;
    private long leftTime = 0;
    private LeftTimeThread thread;

    public void setOnWaitingRoomListener(SendDataToActivity listener) {
        this.listener = listener;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new DictionaryInfoAdapter(getContext(), true);
        mb.rvTestWaitingDictPreview.setAdapter(adapter);
        readDB();


    }

    private void readDB() {
        Util.myRefWord.child(userId).child(roomKey).child("list").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Log.e(TAG, "onDataChange: " + snapshot.getValue());
                for (DataSnapshot item : snapshot.getChildren()) {
                    DictionaryWordItem getItem = item.getValue(DictionaryWordItem.class);
                    if (getItem != null) {
                        DictionaryWordItem wordItem = new DictionaryWordItem(
                                getItem.getKor(),
                                getItem.getEng()
                        );
                        wordItem.setIndex(adapter.getItemCount() + 1);
                        adapter.addItem(wordItem);
                        adapter.notifyItemInserted(adapter.getItemCount() - 1);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity)getActivity();
        mb = FragmentTestWaitingRoomBinding.inflate(getLayoutInflater());
        Bundle bundle = getArguments();
        SimpleDateFormat dateFormat = new SimpleDateFormat("y/MM/dd hh:mm");
        if (bundle != null) {
            item = (OnlineTestItem) bundle.getSerializable("item");
            Log.e(TAG, "onCreate: " + item.getTitle());
            roomKey = item.getRoomKey();
            userId = item.getHostId();
            mb.tvTestRoomTitle.setText(item.getTitle());
            mb.tvTestRoomStartTime.setText("시작시간 :"+item.getStartTime());
            mb.tvTimeLeft.setText("시험시간 :"+item.getEndTime());
            try {
                Calendar date1 = Calendar.getInstance();
                Date testDate = dateFormat.parse(item.getStartTime());
                if (testDate != null) {
                    date1.setTime(testDate);
                }

                Calendar date2 = Calendar.getInstance();
                Date currentDate = dateFormat.parse(dateFormat.format(new Date()));
                if (currentDate != null) {
                    date2.setTime(currentDate);
                }
                leftTime = (date1.getTimeInMillis() - date2.getTimeInMillis()) / 1000;

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if (leftTime < 0) {
            testAvaliable = true;
            mb.btnWaitingRoomTest.setText("테스트 시작");
        }
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mb.btnWaitingRoomTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testAvaliable) {
                    listener.sendTestingData(
                            item.getHostId(),
                            item.getRoomKey(),
                            item.getMaxCount(),
                            1,
                            item.getHost(),
                            item.getTitle()
                    );

                    Toast.makeText(getContext(), "입장", Toast.LENGTH_SHORT).show();
                    activity.changeFragment(Const.TEST_MY_FRAGMENT);

                } else {
                    Toast.makeText(getContext(), "아직 테스트 시간이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mb.btnWaitingRoomViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

         thread = new LeftTimeThread();
        if (leftTime < 0) {


        } else {
            thread.start();
        }
        return mb.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        thread.stop=true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    class LeftTimeThread extends Thread {
        private boolean stop = false;
        @Override
        public void run() {
            mb.btnWaitingRoomTest.setText("");
            while (leftTime != 0) {
                if (stop){
                    break;
                }
                leftTime -= 1;
                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("date", leftTime / 60 + "분" + leftTime % 60 + "초 남았습니다.");
                message.setData(bundle);
                handler.handleMessage(message);
                Log.e(TAG, "run: " + leftTime / 60 + " " + leftTime % 60);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("date","테스트시작");
            message.setData(bundle);
            handler.handleMessage(message);
            testAvaliable = true;


        }

    }

    class TimeHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String timeDate = msg.getData().getString("date");
            mb.btnWaitingRoomTest.setText(timeDate);

        }
    }
}
