package com.mashup.friendlycoding.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.mashup.friendlycoding.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startLoading()
    }

    private fun startLoading() {
        val handler = Handler()
        val intent = Intent(this, TitleActivity::class.java)
        handler.postDelayed({
            startActivity(intent)
            finish()
        }, 2000)
    }
}
