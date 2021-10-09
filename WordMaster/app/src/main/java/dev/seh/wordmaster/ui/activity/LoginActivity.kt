package dev.seh.wordmaster.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.seh.wordmaster.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val mBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}