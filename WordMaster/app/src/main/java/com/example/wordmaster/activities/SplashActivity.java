package com.example.wordmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.SharedManger;
import com.example.wordmaster.R;
import com.example.wordmaster.databinding.ActivitySplashBinding;

/**
 * @author seungho
 * @since 2021-07-08
 * class SplashActivity.java
 * project WordMaster
 * github devaspirant0510
 * email seungho020510@gmail.com
 * description
 **/
public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedManger.init(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        binding.tvAppName.setAnimation(animation);
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                String user = SharedManger.loadData(Const.SHARED_USER_ID,"");
                Intent intent;
                if (!user.equals("")) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }else{
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        },2800);

    }
}
