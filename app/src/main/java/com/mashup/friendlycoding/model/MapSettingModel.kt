package com.mashup.friendlycoding.model

import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.viewmodel.CodeBlock

class Drawables(val backgroundImg : Int = R.drawable.grid2, var princessImg : Int = R.drawable.princess) {
    var monsterImg = 0
    var itemImg : ArrayList<Array<Int>>? = null
}

class Stage(val map : Map, val princess : Princess, val monster: Monster, val offeredBlock : ArrayList<CodeBlock>, val bossBattleBlock : ArrayList<CodeBlock>? = null)

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
                    arrayOf(0, 0, 3, 0, 0, 1, 0, 0, 0, 2)    // 클리어되는 곳을 일단 2로 설정함, 3은 곡괭이
                )

                // 드로어블
                val mDrawables = Drawables()
                mDrawables.monsterImg = R.drawable.monster
                mDrawables.itemImg = arrayListOf(arrayOf(3, R.drawable.pick_axe, 2, 9))    // 순서대로 아이템의 종류, 아이템의 이미지, 아이템의 y, x

                // 기본 제공되는 블록
                val offeredBlock = arrayListOf(
                    CodeBlock("move"),
                    CodeBlock("turnLeft"),
                    CodeBlock("turnRight"),
                    CodeBlock("pickAxe"),
                    CodeBlock("for", type = 1),
                    CodeBlock("}", type = 4),
                    CodeBlock("if", type = 2),
                    CodeBlock("isPickAxe", type = 3)
                )

                val bossBattleBlock = arrayListOf(
                    CodeBlock("if", type = 2),
                    CodeBlock("while", type = 2),
                    CodeBlock("isKilled", type = 3),
                    CodeBlock("detectedFire", type = 3),
                    CodeBlock("detectedWater", type = 3),
                    CodeBlock("}", type = 4)
                    )

                return Stage(Map(mapList, mDrawables), Princess(10), Monster(0, 0, 0), offeredBlock)
            }

            1-> {
                val mapList = arrayOf(
                    arrayOf(0, 0, 0, 0, 1, 0, 1, 0, 0, 0),
                    arrayOf(1, 1, 0, 0, 1, 0, 1, 1, 1, 1),
                    arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0),
                    arrayOf(1, 0, 1, 1, 1, 0, 1, 1, 1, 1),
                    arrayOf(1, 0, 0, 4, 0, 0, 0, 0, 0, 0),
                    arrayOf(1, 0, 0, 0, 0, 1, 1, 1, 1, 0),
                    arrayOf(1, 1, 1, 1, 0, 1, 0, 0, 4, 0),
                    arrayOf(0, 0, 0, 0, 0, 1, 0, 1, 0, 0),
                    arrayOf(0, 1, 1, 1, 1, 1, 0, 1, 1, 1),
                    arrayOf(0, 0, 4, 0, 0, 1, 0, 0, 0, 2)  // 4는 버섯
                )

                // 드로어블
                val mDrawables = Drawables(backgroundImg = R.drawable.test_image)
                mDrawables.monsterImg = R.drawable.monster
                mDrawables.itemImg = arrayListOf(
                    arrayOf(4, R.drawable.mushroom, 2, 9),
                    arrayOf(4, R.drawable.mushroom, 3, 4),
                    arrayOf(4, R.drawable.mushroom, 8, 6)
                    )

                // 기본 제공되는 블록
                val blockButton = arrayListOf(
                    CodeBlock("move"),
                    CodeBlock("turnLeft"),
                    CodeBlock("turnRight"),
                    CodeBlock("if"),
                    CodeBlock("for"),
                    CodeBlock("isMushroom"),
                    CodeBlock("eatMushRoom"),
                    CodeBlock("}")
                )

                return Stage(Map(mapList, mDrawables), Princess(10), Monster(0, 0, 0), blockButton)
            }

            // 2 -> {} ...

            else -> {
                return Stage(Map(), Princess(10), Monster(0, 0, 0), arrayListOf())
            }
        }
    }
}