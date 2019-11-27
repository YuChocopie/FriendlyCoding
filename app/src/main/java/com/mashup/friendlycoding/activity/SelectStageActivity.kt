package com.mashup.friendlycoding.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.databinding.ActivityPlayBinding
import com.mashup.friendlycoding.databinding.ActivitySelectStageBinding
import com.mashup.friendlycoding.viewmodel.SelectActViewModel
import com.mashup.friendlycoding.viewmodel.SelectStageViewModel
import kotlinx.android.synthetic.main.activity_select_stage.*

class SelectStageActivity : BaseActivity() {
    var numberCheck = 10
    val mSelectStageViewModel = SelectStageViewModel()
    var check = 10
    lateinit var binding: ActivitySelectStageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actNum = intent.getIntExtra("actNum", 1)
        binding = DataBindingUtil.setContentView<ActivitySelectStageBinding>(
            this,
            R.layout.activity_select_stage
        )
        binding.act = actNum
        binding.lifecycleOwner = this
        mSelectStageViewModel.init()
        binding.selectStageVM = mSelectStageViewModel

        mSelectStageViewModel.stageToStart.observe(this, Observer {
            if (it != -1) {
                val intent = Intent(this, PlayActivity::class.java)
                intent.putExtra("stageNum", actNum + it)
                Log.e("stagenumber", "${actNum}+$it")
                numberCheck = actNum + it
                startActivityForResult(intent, numberCheck)
            }
        })

        //1. 초기화시 확인
        val sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)
        check = sharedPreferences.getInt("key", 10)

        when (check % 10) {
            0 -> {
                //아무것도 깨지 않았을 경우
                mSelectStageViewModel.setColor(binding.stage1)
                mSelectStageViewModel.grayColor(binding.stage2)
                mSelectStageViewModel.grayColor(binding.stage3)
            }
            1 -> {
                mSelectStageViewModel.setColor(binding.stage1)
                mSelectStageViewModel.setColor(binding.stage2)
                mSelectStageViewModel.grayColor(binding.stage3)
            }
            2 -> {
                mSelectStageViewModel.setColor(binding.stage1)
                mSelectStageViewModel.setColor(binding.stage2)
                mSelectStageViewModel.setColor(binding.stage3)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == numberCheck) {
            Log.e("성공하고 돌아옴", "$numberCheck")
            when (numberCheck % 10) {
                1 -> {
                    mSelectStageViewModel.setColor(binding.stage2)
                    val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
                    val editor = pref.edit()
                    editor.putInt("key", numberCheck)
                    editor.apply()
                }
                2 -> {
                    mSelectStageViewModel.setColor(binding.stage3)
                    val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
                    val editor = pref.edit()
                    editor.putInt("key", numberCheck)
                    editor.apply()
                }
                3 -> {
                    val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
                    val editor = pref.edit()
                    editor.putInt("key", numberCheck + 7)
                    editor.apply()
                }
            }
        }
    }
}