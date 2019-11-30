package com.smu.friendlycoding.model

import com.smu.friendlycoding.R

class MapDrawable(var backgroundImg : Int = R.drawable.bg_stage02,
                  var princessImg : Int = R.drawable.princess_right,
                  var axeImg : Int = R.drawable.ic_pick_axe,
                  var princessX : Int=0,
                  var princessY : Int=9
                  ) {
    var monsterImg = 0
    var bossBattleBackgroundImg = 0
    var item = arrayListOf<MapItem>()
}