package dev.seh.wordmaster.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import dev.seh.wordmaster.R
import dev.seh.wordmaster.base.BaseActivity
import dev.seh.wordmaster.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val anim = AnimationUtils.loadAnimation(this,R.anim.splash_anim)
        mBinding.tvAppName.animation = anim

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }, 2500)
    }
}