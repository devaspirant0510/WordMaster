package com.example.wordmaster.view.dialog.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.wordmaster.databinding.DialogTimeSettingBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author seungho
 * @since 2021-06-23
 * project com.example.wordmaster.fragment
 * class DateTimeSettingDialog.java
 * github devaspirant0510
 * email seungho020510@gmail.com
 * description 온라인 테스트 프레그먼트에서 시간 설정해주는 다이얼로그
 **/
public class DateTimeSettingDialog extends Dialog {
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";

    private DialogTimeSettingBinding mb;
    private static final String TAG = "DateTimeSettingDialog";
    private DateTimeCallBack callBack = null;
    private String mode;

    public DateTimeSettingDialog(@NonNull Context context) {
        super(context);
    }

    public void setDateTimeCallback(DateTimeCallBack callBack){
        this.callBack =callBack;
    }

    public interface DateTimeCallBack{
        void startTimeCallback(String date);
        void endTimeCallback(String date);
    }
    public void setMode(String mode){
        this.mode = mode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = DialogTimeSettingBinding.inflate(getLayoutInflater());
        setContentView(mb.getRoot());
        DatePicker datePicker = mb.dpTimeSettingDatePicker;
        TimePicker timePicker = mb.tpTimeSettingTimePicker;
        if(mode.equals(START_TIME)){
            mb.btnTimeNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mb.tpTimeSettingTimePicker.getVisibility()==View.VISIBLE){
                        SimpleDateFormat dateFormat = new SimpleDateFormat("y/MM/dd hh:mm");
                        Calendar calendar =Calendar.getInstance();
                        calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),
                                timePicker.getHour(),timePicker.getMinute());
                        String getDate = dateFormat.format(calendar.getTime());
                        Toast.makeText(getContext(),getDate,Toast.LENGTH_SHORT).show();
                        if (mode.equals(START_TIME)){
                            callBack.startTimeCallback(getDate);
                        }else{
                            callBack.endTimeCallback(getDate);
                        }
                        mb.dpTimeSettingDatePicker.setVisibility(View.VISIBLE);
                        mb.tpTimeSettingTimePicker.setVisibility(View.GONE);

                        dismiss();

                    }else{
                        mb.dpTimeSettingDatePicker.setVisibility(View.GONE);
                        mb.tpTimeSettingTimePicker.setVisibility(View.VISIBLE);
                    }

                }
            });
            mb.btnTimePrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mb.dpTimeSettingDatePicker.getVisibility()==View.VISIBLE){
                        dismiss();
                    }
                    else{
                        mb.dpTimeSettingDatePicker.setVisibility(View.VISIBLE);
                        mb.tpTimeSettingTimePicker.setVisibility(View.GONE);
                    }
                }
            });


        }else{
            mb.tpTimeSettingTimePicker.setIs24HourView(true);
            mb.tpTimeSettingTimePicker.setHour(0);
            mb.tpTimeSettingTimePicker.setMinute(0);
            mb.tvTimeTitle.setText("시험시간 설정");
            mb.dpTimeSettingDatePicker.setVisibility(View.GONE);
            mb.tpTimeSettingTimePicker.setVisibility(View.VISIBLE);
            mb.btnTimeNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String date = mb.tpTimeSettingTimePicker.getHour()+":"+mb.tpTimeSettingTimePicker.getMinute();
                    callBack.endTimeCallback(date);
                    dismiss();

                }
            });
            mb.btnTimePrevious.setText("취소");
            mb.btnTimePrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

    }
}
