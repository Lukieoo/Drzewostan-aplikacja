package com.anioncode.drzewostan.features

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.anioncode.drzewostan.R
import com.anioncode.drzewostan.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    private val preferences: SharedPreferences =
        getSharedPreferences("FirstTimeLogin", Activity.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (preferences.getBoolean("FirstTimeLogin", false)) {
            preferences.edit().putBoolean("FirstTimeLogin", true)
            preferences.edit().apply()
            splashAnimation(3)
        } else {
            splashAnimation(1)
        }
    }

    private fun splashAnimation(sec: Long) {
        val animation = AnimationUtils.loadAnimation(
            applicationContext, R.anim.fade
        )
        binding.image.startAnimation(animation)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, sec * 1000)
    }
}