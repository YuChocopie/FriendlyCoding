package com.mashup.friendlycoding.viewmodel

import androidx.lifecycle.ViewModel
import com.mashup.friendlycoding.model.Drawables
import com.mashup.friendlycoding.model.MapSettingModel

class MapSettingViewModel :ViewModel(){
    val mMapSettingModel = MapSettingModel()
    var mDrawables = Drawables()
    var offeredBlock = arrayListOf<CodeBlock>()
    var bossBattleBlock : ArrayList<CodeBlock>? = null
}