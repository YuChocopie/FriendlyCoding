package com.mashup.friendlycoding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mashup.friendlycoding.databinding.ActivityTutorialBinding
import com.mashup.friendlycoding.viewmodel.TutorialViewModel

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
