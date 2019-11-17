package com.mashup.friendlycoding.viewmodel

import android.os.Build
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.mashup.friendlycoding.activity.PlayActivity
import com.mashup.friendlycoding.model.*
import kotlinx.android.synthetic.main.activity_play.*

class MapSettingViewModel :ViewModel(){
    val mMapSettingModel = MapSettingModel()
    var mDrawables = MapDrawable()
    var offeredBlock = arrayListOf<CodeBlock>()
    var bossBattleBlock : ArrayList<CodeBlock>? = null
    lateinit var playActivity: PlayActivity
    var width = 0
    var oneBlock = 0f

    fun setStage(
        stageInfo: Stage, playActivity: PlayActivity
    ) {
        offeredBlock = stageInfo.offeredBlock
        bossBattleBlock = stageInfo.bossBattleBlock
        mDrawables = stageInfo.map.drawables!!
        this.playActivity = playActivity
        Log.e(" mDrawables", mDrawables.item.size.toString())
        Log.e(" offeredBlocksize", offeredBlock.size.toString())
    }

    private fun setMapItem() {
        Log.e("123", mDrawables.item.size.toString())
        if (mDrawables.item.size > 0)
            settingImg(playActivity.item_1, mDrawables.item[0])
        if (mDrawables.item.size > 1)
            settingImg(playActivity.item_2, mDrawables.item[1])
        if (mDrawables.item.size > 2)
            settingImg(playActivity.item_3, mDrawables.item[2])
        if (mDrawables.item.size > 3)
            settingImg(playActivity.item_4, mDrawables.item[3])
        if (mDrawables.item.size > 4)
            settingImg(playActivity.item_5, mDrawables.item[4])
        if (mDrawables.item.size > 5)
            settingImg(playActivity.item_6, mDrawables.item[5])
    }

    private fun settingImg(iv: ImageView, item: MapItem) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iv.setImageDrawable(playActivity.getDrawable(item.resourceId))
        }
        iv.visibility = item.visibility
        iv.x = item.Y.toFloat() * oneBlock
        iv.y = item.X.toFloat() * oneBlock
    }

    fun setViewSize(width: Int) {
        this.width = width
        oneBlock = (width / 10 + width % 10).toFloat()
        setMapItem()
    }


}