package com.mashup.friendlycoding.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.databinding.ActivityPlayBinding
import com.mashup.friendlycoding.databinding.ActivitySelectStageBinding
import com.mashup.friendlycoding.viewmodel.SelectStageViewModel
import kotlinx.android.synthetic.main.activity_select_stage.*

class SelectStageActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actNum = intent.getIntExtra("actNum", 1)
        val binding = DataBindingUtil.setContentView<ActivitySelectStageBinding>(this, R.layout.activity_select_stage)
        binding.act = actNum
        binding.lifecycleOwner = this
        val mSelectStageViewModel = SelectStageViewModel()
        mSelectStageViewModel.init()
        binding.selectStageVM = mSelectStageViewModel

        mSelectStageViewModel.stageToStart.observe(this, Observer {
            if (it != -1) {
                val intent = Intent(this, PlayActivity::class.java)
                intent.putExtra("stageNum", actNum+it)
                startActivity(intent)
            }
        })
    }
}