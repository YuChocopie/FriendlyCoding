package com.mashup.friendlycoding.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.databinding.ActivityStoryBinding
import com.mashup.friendlycoding.viewmodel.StoryViewModel
import java.lang.Thread.sleep

class StoryActivity : BaseActivity() {
    private var mStoryViewModel = StoryViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityStoryBinding>(this,
            R.layout.activity_story
        )
        binding.lifecycleOwner = this
        binding.storyVM = mStoryViewModel

        val stageNum = intent.getIntExtra("stageNum", 11)
        mStoryViewModel.init(stageNum)

        mStoryViewModel.page.observe(this, Observer<Int> { t ->
            Log.e("페이지", "$t")
            if (t == 3) {
                //sleep(500)
                finish()
            }
            else {
                try {
                    binding.princessScript.text = mStoryViewModel.script!![t]
                }catch (e:ArrayIndexOutOfBoundsException ){

                }
                if (t == 2) {
                    //TODO :: 교준
                    binding.storyNext.setImageResource(R.drawable.ic_arrow_forward_black_24dp)
                }
                else {
                    binding.storyNext.setImageResource(R.drawable.ic_arrow_forward_black_24dp)
                }
            }
        })
    }
}
