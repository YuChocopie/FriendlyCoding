package com.mashup.friendlycoding.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.bindSRC
import com.mashup.friendlycoding.databinding.ActivityMainBinding
import com.mashup.friendlycoding.viewmodel.SelectActViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    lateinit var mSelectActViewModel : SelectActViewModel
    var check = 2
    var key = "key"
    lateinit var binding : ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mSelectActViewModel = SelectActViewModel(this.application)
        this.check = mSelectActViewModel.checkNum
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.stageVM = this.mSelectActViewModel
        this.mSelectActViewModel.init()
        val animation =
            AnimationUtils.loadAnimation(this, R.anim.cloudanimation)
        val animation1 =
            AnimationUtils.loadAnimation(this, R.anim.cloudanimation1)
//        cloud2.startAnimation(animation)
//        cloud3.startAnimation(animation)
//        cloud4.startAnimation(animation)
//        cloud6.startAnimation(animation1)
//        cloud7.startAnimation(animation1)
//        cloud8.startAnimation(animation1) // TODO : 빼는 방법을 고안해보자

        mSelectActViewModel.actToStart.observe(this, Observer {
            if (it != -1) {
                val intent = Intent(this, SelectStageActivity::class.java)
                intent.putExtra("actNum", it)
                this.startActivity(intent)
            }
        })
        Log.e("현재 check","$check")
        saveStage(key,check)
    }

    override fun onRestart() {
        super.onRestart()
        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        this.check = pref.getInt(key, 0)
        Log.e("Restart check","${this.check}")
        this.mSelectActViewModel.check = this.check
        binding.stageVM!!.init()
        binding.stageVM = this.mSelectActViewModel
        binding.stageRC
    }

    fun saveStage(key: String, value: Int) {
        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt(key, value)
        editor.apply()
    }
}