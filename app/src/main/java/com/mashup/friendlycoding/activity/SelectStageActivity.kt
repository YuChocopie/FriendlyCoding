package com.mashup.friendlycoding.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.databinding.ActivitySelectStageBinding
import com.mashup.friendlycoding.viewmodel.BattleViewModel
import com.mashup.friendlycoding.viewmodel.StageViewModel

class SelectStageActivity : BaseActivity() {

//    private var mStageViewModel = StageViewModel()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_stage)
        val binding = DataBindingUtil.setContentView<ActivitySelectStageBinding>(this,
            R.layout.activity_select_stage
        )
        binding.lifecycleOwner = this
//        binding.stageVM = mStageViewModel

    }
}
