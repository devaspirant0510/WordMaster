package com.example.wordmaster.view.fragment;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.SharedManger;
import com.example.wordmaster.adapter.ActivityBoardAdapter;
import com.example.wordmaster.databinding.FragmentProfileBinding;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding mb;
    private String profileURI;
    private ActivityBoardAdapter adapter;
    private static final String TAG = "ProfileFragment";
    private LocalDate currentDate;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentProfileBinding.inflate(getLayoutInflater());
        profileURI = SharedManger.loadData(Const.SHARED_USER_PROFILE_URI, "");
        mb.ivProfile.setBackground(new ShapeDrawable(new OvalShape()));
        mb.ivProfile.setClipToOutline(true);
        Date date = new Date();
        SimpleDateFormat currentFormat = new SimpleDateFormat("yyyy년 MM월");
        mb.tvActivityBoardDate.setText(currentFormat.format(date));
        Log.e(TAG, "onCreate: "+profileURI );
        Glide.with(getContext()).load(profileURI).into(mb.ivProfile);
        init();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
            initDateSetting(currentDate);
        }

    }

    private void adapterUpdate(int start, int maxDay) {
        adapter.adapterClear();
        if (start != 6) {
            for (int i = 0; i < start + 1; i++) {
                adapter.addItem(Const.ACTIVITY_GRID_EMPTY);
            }

        }
        for (int i = 0; i < maxDay; i++) {
            adapter.addItem(Const.ACTIVITY_GRID_NONE);

        }
        mb.gridRvActivityBoard.setAdapter(adapter);

    }

    private void initDateSetting(LocalDate calendar) {
        // 날짜를 n 년 n 월 1일로 바꾸고 현재 요일을 구해서 공백을 구함
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int year = calendar.getYear();
            Month dateMonth = calendar.getMonth();
            int month = dateMonth.getValue();
            int maxDay = dateMonth.maxLength();
            int day = calendar.getDayOfMonth();
            LocalDate newDate = LocalDate.of(year, month, 1);

            Log.e(TAG, "initDateSetting: " + newDate.getDayOfWeek());


            adapterUpdate(changeDayOfWeek2Int(newDate.getDayOfWeek().toString()), maxDay);

        }


    }

    private int changeDayOfWeek2Int(String weekday) {
        switch (weekday) {
            case "MONDAY":
                return 0;
            case "TUESDAY":
                return 1;
            case "WEDNESDAY":
                return 2;
            case "THURSDAY":
                return 3;
            case "FRIDAY":
                return 4;
            case "SATURDAY":
                return 5;
            case "SUNDAY":
                return 6;
            default:
                return -1;

        }
    }

    private void init() {
        GridLayoutManager gm = new GridLayoutManager(getContext(), 7, RecyclerView.VERTICAL, false);
        mb.gridRvActivityBoard.setLayoutManager(gm);
        adapter = new ActivityBoardAdapter();
        mb.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    int year = currentDate.getYear();
                    int month;
                    if(currentDate.getMonth().getValue()+1>12){
                        month = 1;
                        year+=1;

                    }else{
                         month = currentDate.getMonth().getValue()+1;

                    }
                    int day = currentDate.getDayOfMonth();
                    currentDate = currentDate.withYear(year);
                    currentDate =  currentDate.withDayOfMonth(day);
                    currentDate = currentDate.withMonth(month);
                    mb.tvActivityBoardDate.setText(year+"년 "+month+"월");
                    Log.e(TAG, "onClick: "+currentDate );
                    LocalDate date = LocalDate.of(year,month,day);
                    initDateSetting(date);


                }
            }
        });
        mb.btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    int year = currentDate.getYear();
                    int month;
                    if(currentDate.getMonth().getValue()-1<1){
                        month = 12;
                        year-=1;

                    }else{
                        month = currentDate.getMonth().getValue()-1;
                    }
                    int day = currentDate.getDayOfMonth();
                    Log.e(TAG, "onClick: "+currentDate );
                    currentDate = currentDate.withYear(year);
                    currentDate = currentDate.withDayOfMonth(day);
                    currentDate = currentDate.withMonth(month);
                    mb.tvActivityBoardDate.setText(year+"년 "+month+"월");
                    LocalDate date = LocalDate.of(year,month,day);
                    initDateSetting(date);
                }
            }
        });


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mb.tvProfileNickname.setText(SharedManger.loadData(Const.SHARED_USER_NAME,""));
        return mb.getRoot();

    }
}
