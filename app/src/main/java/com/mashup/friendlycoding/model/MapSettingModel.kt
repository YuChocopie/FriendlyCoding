package com.mashup.friendlycoding.model

import com.mashup.friendlycoding.R

class Drawables(val backgroundImg : Int = 0) {
    var monsterImg = 0
    var itemImg = 0
}

class MapSettingModel {
    fun getMapInfo (stageNum : Int) : Map {
        when (stageNum) {
            0 -> {
                // 맵 정보
                val mapList = arrayOf(
                    arrayOf(0, 0, 0, 0, 1, 0, 1, 0, 0, 0),
                    arrayOf(1, 1, 0, 0, 1, 0, 1, 1, 1, 1),
                    arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0),
                    arrayOf(1, 0, 1, 1, 1, 0, 1, 1, 1, 1),
                    arrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                    arrayOf(1, 0, 0, 0, 0, 1, 1, 1, 1, 0),
                    arrayOf(1, 1, 1, 1, 0, 1, 0, 0, 0, 0),
                    arrayOf(0, 0, 0, 0, 0, 1, 0, 1, 0, 0),
                    arrayOf(0, 1, 1, 1, 1, 1, 0, 1, 1, 1),
                    arrayOf(0, 0, 0, 0, 0, 1, 0, 0, 0, 2)    // 클리어되는 곳을 일단 2로 설정함
                )

                // 드로어블
                val mDrawables = Drawables(R.drawable.grid2)
                mDrawables.monsterImg = R.drawable.monster

                return Map(mapList, mDrawables)
            }

            // 1 -> {} ...
            // 2 -> {} ...

            // 없으면 빨간 줄 떠서 임시로 넣었음
            else -> {
                // 맵 정보
                val mapList = arrayOf(
                    arrayOf(0, 0, 0, 0, 1, 0, 1, 0, 0, 0),
                    arrayOf(1, 1, 0, 0, 1, 0, 1, 1, 1, 1),
                    arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0),
                    arrayOf(1, 0, 1, 1, 1, 0, 1, 1, 1, 1),
                    arrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                    arrayOf(1, 0, 0, 0, 0, 1, 1, 1, 1, 0),
                    arrayOf(1, 1, 1, 1, 0, 1, 0, 0, 0, 0),
                    arrayOf(0, 0, 0, 0, 0, 1, 0, 1, 0, 0),
                    arrayOf(0, 1, 1, 1, 1, 1, 0, 1, 1, 1),
                    arrayOf(0, 0, 0, 0, 0, 1, 0, 0, 0, 2)    // 클리어되는 곳을 일단 2로 설정함
                )

                // 드로어블
                val mDrawables = Drawables(R.drawable.grid2)
                mDrawables.itemImg = R.drawable.monster

                return Map(mapList, mDrawables)
            }
        }
    }
}