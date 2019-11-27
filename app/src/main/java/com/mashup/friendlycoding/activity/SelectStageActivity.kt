package com.mashup.friendlycoding.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.databinding.ActivitySelectStageBinding
import com.mashup.friendlycoding.viewmodel.SelectStageViewModel

class SelectStageActivity : BaseActivity() {
    var numberCheck = 10
    val mSelectStageViewModel = SelectStageViewModel()
    var check = 10
    var save = 0
    var checkTemp = 0
    var saveTemp = 0
    lateinit var binding: ActivitySelectStageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_stage)

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

        mSelectStageViewModel.setColor(binding.stage1)
        mSelectStageViewModel.grayColor(binding.stage2)
        mSelectStageViewModel.grayColor(binding.stage3)

        //1. 초기화시 확인
        val sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)
        check = sharedPreferences.getInt("key", 10)

        val sharedPreferencesSave = getSharedPreferences("prefSave", Context.MODE_PRIVATE)
        save = sharedPreferencesSave.getInt("keySave", 0)
        Log.e("save값:", "$save")
        Log.e("actNum값:", "$actNum")

        when (save) {
            1 -> {
                if (actNum / 10 == 0) {
                    mSelectStageViewModel.setColor(binding.stage1)
                    mSelectStageViewModel.setColor(binding.stage2)
                    mSelectStageViewModel.setColor(binding.stage3)
                }
            }
            2 -> {
                if (actNum / 10 == 1) {
                    mSelectStageViewModel.setColor(binding.stage1)
                    mSelectStageViewModel.setColor(binding.stage2)
                    mSelectStageViewModel.setColor(binding.stage3)
                }
            }
            3 -> {
                if (actNum / 10 == 1 || actNum / 10 == 2) {
                    mSelectStageViewModel.setColor(binding.stage1)
                    mSelectStageViewModel.setColor(binding.stage2)
                    mSelectStageViewModel.setColor(binding.stage3)
                }
            }
            4 -> {
                if (actNum / 10 == 1 || actNum / 10 == 2 || actNum / 10 == 3) {
                    mSelectStageViewModel.setColor(binding.stage1)
                    mSelectStageViewModel.setColor(binding.stage2)
                    mSelectStageViewModel.setColor(binding.stage3)
                }
            }
            5 -> {
                if (actNum / 10 == 1 || actNum / 10 == 2 || actNum / 10 == 3 || actNum / 10 == 4) {
                    mSelectStageViewModel.setColor(binding.stage1)
                    mSelectStageViewModel.setColor(binding.stage2)
                    mSelectStageViewModel.setColor(binding.stage3)
                }
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
                    this.checkTemp = pref.getInt("pref", 0)
                    if (this.checkTemp < numberCheck) {
                        val editor = pref.edit()
                        editor.putInt("key", numberCheck)
                        editor.apply()
                    }
                }
                2 -> {
                    mSelectStageViewModel.setColor(binding.stage3)
                    val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
                    this.checkTemp = pref.getInt("pref", 0)
                    if (this.checkTemp < numberCheck) {
                        val editor = pref.edit()
                        editor.putInt("key", numberCheck)
                        editor.apply()
                    }
                }
                3 -> {
                    val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
                    this.checkTemp = pref.getInt("pref", 0)
                    if (this.checkTemp < numberCheck) {
                        val editor = pref.edit()
                        editor.putInt("key", numberCheck + 7)
                        editor.apply()
                    }

                    val prefSave = getSharedPreferences("prefSave", Context.MODE_PRIVATE)
                    this.saveTemp = prefSave.getInt("prefSave",0)
                    if(this.saveTemp < save){
                        val editorSave = prefSave.edit()
                        editorSave.putInt("keySave", (numberCheck + 7) / 10)
                        editorSave.apply()
                    }
                }
            }
        }
    }
}