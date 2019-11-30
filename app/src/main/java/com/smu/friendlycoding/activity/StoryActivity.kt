package com.smu.friendlycoding.activity

import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.smu.friendlycoding.R
import com.smu.friendlycoding.databinding.ActivityStoryBinding
import com.smu.friendlycoding.viewmodel.StoryViewModel
import kotlinx.android.synthetic.main.activity_story.*

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
            if (t == mStoryViewModel.script!!.size) {
                finish()
            }

            else {
                if (t == mStoryViewModel.script!!.size - 2) {
                    if (stageNum == 11) {
                        makeGIF(0)
                    }
                }

                if (t == mStoryViewModel.script!!.size - 1) {
                    if (stageNum == 53) {

                    }

                    if (stageNum == 11) {
                        makeGIF(stageNum)
                    }

                    if (stageNum == 21) {
                        princess.isVisible = false
                        how_to.isVisible = true
                        how_to.setImageResource(R.drawable.how_to_variable)
                    }

                    else if (stageNum == 22) {
                        makeGIF(stageNum)
                    }

                    else if (stageNum == 31) {
                        makeGIF(stageNum)
                    }
                }

                else {
                    if (t != mStoryViewModel.script!!.size - 2 || stageNum != 11) {
                        princess.isVisible = true
                        how_to.isVisible = false
                    }
                }

                try {
                    princess_script.text = mStoryViewModel.script!![t]
                }
                    catch (e:ArrayIndexOutOfBoundsException){
                }
            }
        })
    }

    fun makeGIF(stageNum : Int) {
        princess.isVisible = false
        how_to.isVisible = true
        val gifImage = GlideDrawableImageViewTarget(how_to)
        val ins = when (stageNum) {
            11 -> "delete"
            22 -> "how_to_if"
            31 -> "how_to_for"
            else -> "add"
        }
        val slide = resources.getIdentifier(ins, "drawable", this.packageName)
        Glide.with(this).load(slide).into(gifImage)
    }
}
