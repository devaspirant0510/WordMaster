package com.example.wordmaster.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
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
import com.example.wordmaster.Define.Util;
import com.example.wordmaster.adapter.ActivityBoardAdapter;
import com.example.wordmaster.databinding.FragmentProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding mb;
    private String profileURI;
    private ActivityBoardAdapter adapter;
    private static final String TAG = "ProfileFragment";
    private LocalDate currentDate;
    private int currentYear, currentMonth, currentDay;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentProfileBinding.inflate(getLayoutInflater());
        profileURI = SharedManger.loadData(Const.SHARED_USER_PROFILE_URI, "");
        Log.e(TAG, "onCreate: " + SharedManger.loadData(Const.SHARED_USER_JOIN_DATE, ""));

        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);


    }

    @SuppressLint("SimpleDateFormat")
    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mb.etProfileNickname.setText(SharedManger.loadData(Const.SHARED_USER_NAME, ""));
        mb.etProfileEmail.setText(SharedManger.loadData(Const.SHARED_USER_EMAIL,""));
        return mb.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        event();
    }

    private void initDateSetting() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        // 해당월의 첫째날을 구하기 위해 1로 설정
        calendar.set(currentYear, currentMonth, 1);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        adapterUpdate(startDay, maxDay);
        loadActivityHistoryData(startDay, currentYear, currentMonth);


    }

    private void adapterUpdate(int start, int maxDay) {
        adapter.adapterClear();
        // 첫째주가 일요일로 시작하지 않을경우 (즉 첫째주에 빈칸이 없을때) 공백을 넣을 필요가 없음
        for (int i = 0; i < start - 1; i++) {
            // 첫째주에 날짜 비는 빈칸
            adapter.addItem(Const.ACTIVITY_GRID_EMPTY);
        }
        // 해당월의 최대 날짜 넣기
        for (int i = 0; i < maxDay; i++) {
            adapter.addItem(Const.ACTIVITY_GRID_NONE);
        }
        mb.gridRvActivityBoard.setAdapter(adapter);

    }

    private void init() {
        // 리사이클러뷰 설정 그리드 매니저
        GridLayoutManager gm = new GridLayoutManager(getContext(), 7, RecyclerView.VERTICAL, false);
        mb.gridRvActivityBoard.setLayoutManager(gm);
        adapter = new ActivityBoardAdapter();
        // 이미지뷰 둥글게 처리
        mb.ivProfile.setBackground(new ShapeDrawable(new OvalShape()));
        mb.ivProfile.setClipToOutline(true);
        if(getContext()!=null){
            Glide.with(getContext()).load(profileURI).into(mb.ivProfile);
        }
        // 현재 날짜 설정
        initDateSetting();
        updateDateText(currentYear, currentMonth);
    }

    private void event() {
        mb.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextCalendar();
            }
        });
        mb.btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPreviousCalendar();
            }
        });

    }

    /**
     * 텍스트뷰의 날짜를 업데이트
     *
     * @param year  바뀔 연도
     * @param month 바뀔 월
     */
    private void updateDateText(int year, int month) {
        String currentDateStr = changeToDateFormat(year, month);
        mb.tvActivityBoardDate.setText(currentDateStr);
    }

    /**
     * 년도와 월을 넘기면 알맞는 데이터 포맷으로 변환
     * ex) 2012,3 => 2012년 03월
     *
     * @param year  변환시킬 년도
     * @param month 변환시킬 달
     * @return 변환시킨 결과
     */
    @SuppressLint("SimpleDateFormat")
    private String changeToDateFormat(int year, int month) {
        SimpleDateFormat currentFormat = new SimpleDateFormat("yyyy년 MM월");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        Date date = calendar.getTime();
        return currentFormat.format(date);
    }

    // 저번달 달력 보기
    private void goToPreviousCalendar() {
        String joinDate = SharedManger.loadData(Const.SHARED_USER_JOIN_DATE, null);
        // 가입한 날짜 이전의 달력은 보여주지 않게 하기
//        if (joinDate!=null){
//            String[] joinDateArr = joinDate.split("/");
//            int joinDateYear = Integer.parseInt(joinDateArr[0]);
//            int joinDateMonth = Integer.parseInt(joinDateArr[1]);
//            int joinDateDays = Integer.parseInt(joinDateArr[2]);
//            if(currentYear==joinDateYear && joinDateMonth-1==currentMonth){
//                Toast.makeText(getContext(), "더이상 뒤로갈수 없습니다.", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//        }

        // 현재 월이 1월일경우 12월로 바꿈
        // 년도는 -1
        if (currentMonth == Calendar.JANUARY) {
            currentMonth = Calendar.DECEMBER;
            currentYear -= 1;
        }
        // 나머지 경우는 월만 빼줌
        else {
            currentMonth -= 1;
        }
        initDateSetting();
        updateDateText(currentYear, currentMonth);
    }

    private void goToNextCalendar() {
        // 현재 월이 12월일경우 1월로 바꾸고 년도는 +1
        if (currentMonth == Calendar.DECEMBER) {
            currentMonth = Calendar.JANUARY;
            currentYear += 1;
        } else {
            currentMonth += 1;
        }
        initDateSetting();
        updateDateText(currentYear, currentMonth);
    }

    private void loadActivityHistoryData(int startDay, int year, int month) {
        Log.e(TAG, "loadActivityHistoryData: " + month + " " + startDay);

        Util.myRefUser.child(SharedManger.loadData(Const.SHARED_USER_ID, ""))
                .child("activityHistory").child(String.valueOf(year)).child(String.valueOf(month + 1))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.e(TAG, "onDataChange: " + snapshot.getValue());
                        for (DataSnapshot value : snapshot.getChildren()) {
                            Log.e(TAG, "onDataChange: " + value);
                            Log.e(TAG, "onDataChange: day"+value.getKey());
                            Log.e(TAG, "onDataChange: start day"+startDay );
                            adapter.updateItem(
                                    Integer.parseInt(value.getKey())-1 + startDay-1,
                                    Integer.parseInt(String.valueOf(value.getValue()))
                            );
                            adapter.notifyItemChanged(Integer.parseInt(value.getKey())-1+startDay-1);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


}
