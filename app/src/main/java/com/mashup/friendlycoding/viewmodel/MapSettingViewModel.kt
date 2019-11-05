package com.mashup.friendlycoding.viewmodel

import androidx.lifecycle.ViewModel
import com.mashup.friendlycoding.model.CodeBlock
import com.mashup.friendlycoding.model.MapDrawable
import com.mashup.friendlycoding.model.MapSettingModel

class MapSettingViewModel :ViewModel(){
    val mMapSettingModel = MapSettingModel()
    var mDrawables = MapDrawable()
    var offeredBlock = arrayListOf<CodeBlock>()
    var bossBattleBlock : ArrayList<CodeBlock>? = null

    // TODO : PlayActivity의 뷰 받아서 아이템 뷰 뿌리기... 가능? 가능! 화이팅!
}