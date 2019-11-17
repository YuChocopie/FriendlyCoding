package com.mashup.friendlycoding.activity

 
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.adapter.SelectActAdapter
import com.mashup.friendlycoding.databinding.ActivityMainBinding
import com.mashup.friendlycoding.databinding.ActivityPlayBinding
import com.mashup.friendlycoding.model.StageItem
import com.mashup.friendlycoding.viewmodel.StageViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private var mStageViewModel = StageViewModel()
    var check = mStageViewModel.check
    var key = "key"
    var up = -500F

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        val list = arrayListOf(
            StageItem(R.drawable.bg_stage_map_03,R.drawable.bg_stage_select_03),
            StageItem(R.drawable.bg_stage_map_02,R.drawable.bg_stage_select_02),
            StageItem(R.drawable.bg_stage_map_01,R.drawable.bg_stage_select_01),
            StageItem(R.drawable.bg_stage_map_03,R.drawable.bg_stage_select_03),
            StageItem(R.drawable.bg_stage_map_02,R.drawable.bg_stage_select_02),
            StageItem(R.drawable.bg_stage_map_01,R.drawable.bg_stage_select_01)
        )
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        binding.lifecycleOwner = this
        binding.stageVM = mStageViewModel

        val adapter = SelectActAdapter(this, list,check)
        recyclerviewStage.adapter = adapter

        val lm = LinearLayoutManager(this)
        recyclerviewStage.layoutManager = lm
        recyclerviewStage.scrollToPosition(list.size - 1)

        if (check == 0) {
            recyclerviewStage.scrollToPosition(list.size - 1)
            recyclerviewStage.isLayoutFrozen = true
        } else if (check == 1) {
            cloud2.visibility = View.GONE
            cloud6.visibility = View.GONE
            recyclerviewStage.scrollToPosition(list.size - 2)
            up = -700F
            animateCloud()
            recyclerviewStage.isLayoutFrozen = true
        } else if (check == 2) {
            cloud2.visibility = View.GONE
            cloud6.visibility = View.GONE
            cloud3.visibility = View.GONE
            cloud7.visibility = View.GONE
            recyclerviewStage.scrollToPosition(list.size - 3)
            up = -1000F
            animateCloud()
            recyclerviewStage.isLayoutFrozen = true
        } else if (check == 3) {
            cloud2.visibility = View.GONE
            cloud6.visibility = View.GONE
            cloud3.visibility = View.GONE
            cloud7.visibility = View.GONE
            cloud4.visibility = View.GONE
            cloud8.visibility = View.GONE
            recyclerviewStage.scrollToPosition(list.size - 5)
            up = -700F
            animateCloud()
            recyclerviewStage.isLayoutFrozen = true
        } else if (check == 4) {
            cloud2.visibility = View.GONE
            cloud6.visibility = View.GONE
            cloud3.visibility = View.GONE
            cloud7.visibility = View.GONE
            cloud4.visibility = View.GONE
            cloud8.visibility = View.GONE
            recyclerviewStage.scrollToPosition(list.size - 5)
            up = -900F
            animateCloud()
        }
        val animation =
            AnimationUtils.loadAnimation(this, R.anim.cloudanimation)
        val animation1 =
            AnimationUtils.loadAnimation(this, R.anim.cloudanimation1)

        cloud2.startAnimation(animation)
        cloud3.startAnimation(animation)
        cloud4.startAnimation(animation)

        cloud6.startAnimation(animation1)
        cloud7.startAnimation(animation1)
        cloud8.startAnimation(animation1)
    }

    override fun onPause() {
        super.onPause()
        saveStage(key, check)
    }

    override fun onResume() {
        super.onResume()
        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        check = pref.getInt(key, 0)
    }

    fun saveStage(key: String, value: Int) {
        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun animateCloud() {
//        val cloud: ImageView = findViewById(R.id.cloud)
        val ani = TranslateAnimation(0F, 0F, 0F, up)
        ani.duration = 2000
        ani.fillAfter = true
        ani.startOffset = 2000
        cloud2.startAnimation(ani)
    }
}