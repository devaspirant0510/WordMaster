package dev.seh.wordmaster.ui.activity

import android.content.Intent
import android.os.Bundle
import dev.seh.wordmaster.R
import dev.seh.wordmaster.base.BaseActivity
import dev.seh.wordmaster.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        event();
    }

    private fun event() {
        mBinding.btnKakaoLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
        mBinding.btnGoogleLogin.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}