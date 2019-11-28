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

        //1. 초기화시 확인
        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        check = pref.getInt("key", 10)

        val prefSave = getSharedPreferences("prefSave", Context.MODE_PRIVATE)
        save = prefSave.getInt("keySave", 0)
        Log.e("save값:", "$save")
        Log.e("check값:", "$check")
        Log.e("actNum값:", "$actNum")

        when (save) {
            0 -> {
                if (check / 10 == 0) {
                    mSelectStageViewModel.setColor(binding.stage1)
                    mSelectStageViewModel.grayColor(binding.stage2)
                    mSelectStageViewModel.grayColor(binding.stage3)
                } else if (check / 10 == 1) {
                    if (check % 10 == 0) {
                        mSelectStageViewModel.setColor(binding.stage1)
                        mSelectStageViewModel.grayColor(binding.stage2)
                        mSelectStageViewModel.grayColor(binding.stage3)
                    } else if (check % 10 == 1) {
                        mSelectStageViewModel.setColor(binding.stage1)
                        mSelectStageViewModel.setColor(binding.stage2)
                        mSelectStageViewModel.grayColor(binding.stage3)
                    } else if (check % 10 == 2) {
                        mSelectStageViewModel.setColor(binding.stage1)
                        mSelectStageViewModel.setColor(binding.stage2)
                        mSelectStageViewModel.setColor(binding.stage3)
                    }
                }
                if(actNum >= 20){
                    mSelectStageViewModel.setColor(binding.stage1)
                    mSelectStageViewModel.grayColor(binding.stage2)
                    mSelectStageViewModel.grayColor(binding.stage3)
                }
            }
            2 -> {
                if (actNum < 20) {
                    mSelectStageViewModel.setColor(binding.stage1)
                    mSelectStageViewModel.setColor(binding.stage2)
                    mSelectStageViewModel.setColor(binding.stage3)
                } else if (check / 10 == 2) {
                    if (check % 10 == 0) {
                        mSelectStageViewModel.setColor(binding.stage1)
                        mSelectStageViewModel.grayColor(binding.stage2)
                        mSelectStageViewModel.grayColor(binding.stage3)
                    } else if (check % 10 == 1) {
                        mSelectStageViewModel.setColor(binding.stage1)
                        mSelectStageViewModel.setColor(binding.stage2)
                        mSelectStageViewModel.grayColor(binding.stage3)
                    } else if (check % 10 == 2) {
                        mSelectStageViewModel.setColor(binding.stage1)
                        mSelectStageViewModel.setColor(binding.stage2)
                        mSelectStageViewModel.setColor(binding.stage3)
                    }
                }
                if(actNum >= 30){
                    mSelectStageViewModel.setColor(binding.stage1)
                    mSelectStageViewModel.grayColor(binding.stage2)
                    mSelectStageViewModel.grayColor(binding.stage3)
                }
            }
            3 -> {
                if (actNum < 30) {
                    mSelectStageViewModel.setColor(binding.stage1)
                    mSelectStageViewModel.setColor(binding.stage2)
                    mSelectStageViewModel.setColor(binding.stage3)
                } else if (check / 10 == 3) {
                    if (check % 10 == 0) {
                        mSelectStageViewModel.setColor(binding.stage1)
                        mSelectStageViewModel.grayColor(binding.stage2)
                        mSelectStageViewModel.grayColor(binding.stage3)
                    } else if (check % 10 == 1) {
                        mSelectStageViewModel.setColor(binding.stage1)
                        mSelectStageViewModel.setColor(binding.stage2)
                        mSelectStageViewModel.grayColor(binding.stage3)
                    } else if (check % 10 == 2) {
                        mSelectStageViewModel.setColor(binding.stage1)
                        mSelectStageViewModel.setColor(binding.stage2)
                        mSelectStageViewModel.setColor(binding.stage3)
                    }
                }
                if(actNum >= 40){
                    mSelectStageViewModel.setColor(binding.stage1)
                    mSelectStageViewModel.grayColor(binding.stage2)
                    mSelectStageViewModel.grayColor(binding.stage3)
                }
            }
            4 -> {
                if (actNum < 40) {
                    mSelectStageViewModel.setColor(binding.stage1)
                    mSelectStageViewModel.setColor(binding.stage2)
                    mSelectStageViewModel.setColor(binding.stage3)
                } else if (check / 10 == 4) {
                    if (check % 10 == 0) {
                        mSelectStageViewModel.setColor(binding.stage1)
                        mSelectStageViewModel.grayColor(binding.stage2)
                        mSelectStageViewModel.grayColor(binding.stage3)
                    } else if (check % 10 == 1) {
                        mSelectStageViewModel.setColor(binding.stage1)
                        mSelectStageViewModel.setColor(binding.stage2)
                        mSelectStageViewModel.grayColor(binding.stage3)
                    } else if (check % 10 == 2) {
                        mSelectStageViewModel.setColor(binding.stage1)
                        mSelectStageViewModel.setColor(binding.stage2)
                        mSelectStageViewModel.setColor(binding.stage3)
                    }
                }
                if(actNum >= 50){
                    mSelectStageViewModel.setColor(binding.stage1)
                    mSelectStageViewModel.grayColor(binding.stage2)
                    mSelectStageViewModel.grayColor(binding.stage3)
                }
            }
            5 -> {
                if (actNum < 50) {
                    mSelectStageViewModel.setColor(binding.stage1)
                    mSelectStageViewModel.setColor(binding.stage2)
                    mSelectStageViewModel.setColor(binding.stage3)
                } else if (check / 10 == 5) {
                    if (check % 10 == 0) {
                        mSelectStageViewModel.setColor(binding.stage1)
                        mSelectStageViewModel.grayColor(binding.stage2)
                        mSelectStageViewModel.grayColor(binding.stage3)
                    } else if (check % 10 == 1) {
                        mSelectStageViewModel.setColor(binding.stage1)
                        mSelectStageViewModel.setColor(binding.stage2)
                        mSelectStageViewModel.grayColor(binding.stage3)
                    } else if (check % 10 == 2) {
                        mSelectStageViewModel.setColor(binding.stage1)
                        mSelectStageViewModel.setColor(binding.stage2)
                        mSelectStageViewModel.setColor(binding.stage3)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == numberCheck) {
            if (data == null) {
                return
            } else if (data.getStringExtra("ok") != "ok") {
                return
            }
            Log.e("성공하고 돌아옴", "$numberCheck")
            when (numberCheck % 10) {
                1 -> {
                    mSelectStageViewModel.setColor(binding.stage2)
                    val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
                    checkTemp = pref.getInt("key", 0)
                    Log.e("checkTemp", "$checkTemp, 넘버체크 :$numberCheck")
//                    if (checkTemp < numberCheck) {
                    Log.e("키값 저장:", "$numberCheck")
                    val editor = pref.edit()
                    editor.putInt("key", numberCheck)
                    editor.apply()
//                    }
                }
                2 -> {
                    mSelectStageViewModel.setColor(binding.stage3)
                    val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
                    checkTemp = pref.getInt("key", 0)
//                    if (checkTemp < numberCheck) {
                    Log.e("키값 저장:", "$numberCheck")
                    val editor = pref.edit()
                    editor.putInt("key", numberCheck)
                    editor.apply()
//                    }
                }
                3 -> {
                    val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
                    checkTemp = pref.getInt("key", 0)
//                    if (checkTemp < numberCheck) {
                    Log.e("키값 저장:", "$numberCheck")
                    val editor = pref.edit()
                    editor.putInt("key", numberCheck + 7)
                    editor.apply()
//                    }

                    val prefSave = getSharedPreferences("prefSave", Context.MODE_PRIVATE)
                    saveTemp = prefSave.getInt("keySave", -1)
//                    if (save < numberCheck) {
                    Log.e("save값 저장:", "$numberCheck")
                    val editorSave = prefSave.edit()
                    editorSave.putInt("keySave", (numberCheck + 7) / 10)
                    editorSave.apply()
//                    }
                }
            }
        }
    }
}
