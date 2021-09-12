package com.example.wordmaster.view.dialog.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.SharedManger;
import com.example.wordmaster.Define.Util;
import com.example.wordmaster.databinding.DialogActivityHistoryBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

/**
 * @author : seungHo
 * @since : 2021-09-12
 * class : ActivityHistoryDialog.java
 * github : devaspirant0510
 * email : seungho020510@gmail.com
 * description :
 */
public class ActivityHistoryDialog extends Dialog {
    private static final String TAG = ActivityHistoryDialog.class.getSimpleName();
    private DialogActivityHistoryBinding binding;
    private Calendar calendar;

    public ActivityHistoryDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    private void init(){

        calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        loadActivityHistory(currentYear,currentMonth,currentDay);


    }
    private void loadActivityHistory(int year,int month,int day){
        Util.myRefUser.child(SharedManger.loadData(Const.SHARED_USER_ID,"")).child("activityHistory")
                .child(String.valueOf(year)).child(String.valueOf(month+1)).child(String.valueOf(day))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.getValue()==null){
                            Util.myRefUser.child(SharedManger.loadData(Const.SHARED_USER_ID,""))
                                    .child("activityHistory").child(String.valueOf(year)).child(String.valueOf(month+1)).
                                    child(String.valueOf(day)).setValue(Const.ACTIVITY_GRID_WORST);

                        }else{
                            Log.e(TAG, "onDataChange: "+snapshot);
                            Log.e(TAG, "onDataChange: "+snapshot.getValue() );
                            int getType = Integer.parseInt(String.valueOf(snapshot.getValue()));
                            if(getType!=Const.ACTIVITY_GRID_BEST){
                                Util.myRefUser.child(SharedManger.loadData(Const.SHARED_USER_ID,""))
                                        .child("activityHistory").child(String.valueOf(year)).child(String.valueOf(month+1)).
                                        child(String.valueOf(day)).setValue(getType-1);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}
