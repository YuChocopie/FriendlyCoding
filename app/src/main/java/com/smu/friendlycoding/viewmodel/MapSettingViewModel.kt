package com.smu.friendlycoding.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smu.friendlycoding.activity.PlayActivity
import com.smu.friendlycoding.model.*

open class MapSettingViewModel : ViewModel() {
    val mMapSettingModel = MapSettingModel()
    var mDrawables = MapDrawable()
    var offeredBlock = arrayListOf<CodeBlock>()
    var bossBattleBlock : ArrayList<CodeBlock>? = null
    lateinit var playActivity : PlayActivity
    var width = 0
    var oneBlock = MutableLiveData<Float>()

    fun setStage(stageInfo: Stage, playActivity: PlayActivity) {
        oneBlock.value = 0F
        offeredBlock = stageInfo.offeredBlock
        bossBattleBlock = stageInfo.bossBattleBlock
        mDrawables = stageInfo.map.drawables!!
        this.playActivity = playActivity
    }

    fun setViewSize(width: Int) {
        this.width = width
        this.oneBlock.value = ((width / 10 + width % 10).toFloat())
        Log.e("layout now?", "$width, ${this.oneBlock.value}")
    }

    fun itemSize() : Int {
        return mDrawables.item.size
    }
}