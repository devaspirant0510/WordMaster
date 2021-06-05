package com.example.wordmaster.dialog.bottomsheet;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.wordmaster.Define.Const;
import com.example.wordmaster.databinding.DialogBottomMyTestOptionBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

public class MyTestOptionBottomSheetDialog extends BottomSheetDialog {
    private DialogBottomMyTestOptionBinding mb;
    CallBackOption callBackOption = null;
    public interface CallBackOption{
        void callBack(int option);
    }
    public void setCallBackOption(CallBackOption callBackOption){
        this.callBackOption = callBackOption;
    }


    public MyTestOptionBottomSheetDialog(@NonNull @NotNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = DialogBottomMyTestOptionBinding.inflate(getLayoutInflater());
        setContentView(mb.getRoot());
        clickEvent();

    }
    private void clickEvent(){
        mb.testOptionEng2kor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackOption.callBack(Const.ENG2KOR);
            }
        });
        mb.testOptionKor2eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackOption.callBack(Const.KOR2ENG);
            }
        });
        mb.testOptionRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackOption.callBack(Const.RANDOM);

            }
        });
    }

}
