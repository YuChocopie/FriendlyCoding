package com.mashup.friendlycoding.model

import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.viewmodel.CodeBlock

class Drawables(var backgroundImg : Int = 0) {
    var monsterImg = 0
    var itemImg = 0
    var princessImg = 0
}

class Stage(val map : Map, val princess : Princess, val monster: Monster, val block : ArrayList<CodeBlock>)

class MapSettingModel {
    fun getStageInfo (stageNum : Int) : Stage {
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
                mDrawables.princessImg = R.drawable.princess

                // 기본 제공되는 블록
                val blockButton = arrayListOf(
                    CodeBlock("move"),
                    CodeBlock("turnLeft"),
                    CodeBlock("turnRight"),
                    CodeBlock("pickAxe"),
                    CodeBlock("for"),
                    CodeBlock("}")
                )

                return Stage(Map(mapList, mDrawables), Princess(10), Monster(0, 0, 0), blockButton)
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

                val blockButton = arrayListOf(
                    CodeBlock("move"),
                    CodeBlock("turnLeft"),
                    CodeBlock("turnRight"),
                    CodeBlock("pickAxe"),
                    CodeBlock("for"),
                    CodeBlock("}")
                )

                return Stage(Map(mapList, mDrawables), Princess(10), Monster(0, 0, 0), blockButton)
            }
        }
    }
}