package dev.seh.wordmaster.ui.activity

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import dev.seh.wordmaster.R
import dev.seh.wordmaster.databinding.ActivitySplashBinding

class SplashActivity:AppCompatActivity() {
    private val mBinding by lazy { ActivitySplashBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        val anim = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        mBinding.tvAppName.animation = anim
    }
}