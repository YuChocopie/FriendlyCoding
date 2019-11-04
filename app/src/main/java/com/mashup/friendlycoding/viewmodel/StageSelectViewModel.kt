package com.mashup.friendlycoding.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.mashup.friendlycoding.activity.MainActivity
import com.mashup.friendlycoding.activity.PlayActivity

class StageSelectViewModel : ViewModel(){
    private var mMainActivity : MainActivity? = null

    fun init(context : MainActivity) {
        this.mMainActivity = context
    }

    fun goToPlayActivity(stageNum : Int) {
        val intent = Intent(mMainActivity, PlayActivity::class.java)
        intent.putExtra("stageNum", stageNum)
        mMainActivity!!.startActivity(intent)
    }
}