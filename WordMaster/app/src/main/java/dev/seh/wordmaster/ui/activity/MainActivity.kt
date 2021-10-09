package dev.seh.wordmaster.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.seh.wordmaster.R
import dev.seh.wordmaster.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }
}