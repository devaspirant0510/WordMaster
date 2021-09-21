package com.example.wordmaster.view.dialog.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.wordmaster.databinding.DialogImageSelectionBinding;

/**
 * @author : seungHo
 * @since : 2021-09-20
 * class : ImageSelectDialog.java
 * github : devaspirant0510
 * email : seungho020510@gmail.com
 * description :
 */
public class ImageSelectDialog extends Dialog {
    private static final String TAG = ImageSelectDialog.class.getSimpleName();
    private DialogImageSelectionBinding mBinding;

    public ImageSelectDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DialogImageSelectionBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        init();
        event();

    }
    private void init(){

    }
    private void event(){
        mBinding.tvSelectCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "카메라버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();

            }
        });
        mBinding.tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "갤러리 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
