package com.smu.friendlycoding.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.smu.friendlycoding.R
import com.smu.friendlycoding.databinding.ActivityTutorialBinding
import com.smu.friendlycoding.viewmodel.TutorialViewModel

class TutorialActivity : BaseActivity() {
    private var mTutorialViewModel = TutorialViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityTutorialBinding>(
                this,
                R.layout.activity_tutorial
            )
        binding.lifecycleOwner = this
        binding.tutorialVM = mTutorialViewModel
    }
}
