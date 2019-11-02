package com.mashup.friendlycoding.model

import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.viewmodel.CodeBlock

class Drawables(val backgroundImg : Int = R.drawable.grid2, var princessImg : Int = R.drawable.princess) {
    var monsterImg = 0
    var bossBattleBackgroundImg = 0
    var itemImg = arrayListOf<Array<Any>>()
}

class Stage(val map : Map, val princess : Princess, val monster: Monster?, val offeredBlock : ArrayList<CodeBlock>, val bossBattleBlock : ArrayList<CodeBlock>? = null)

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
                mDrawables.bossBattleBackgroundImg = R.drawable.demonic_castle
                mDrawables.itemImg = arrayListOf(
                    arrayOf(3, "29") // 순서대로 아이템의 종류, 아이템의 y, x
                    //arrayOf(1, "40"),
                    //arrayOf(1, "60")
                )

                // 기본 제공되는 블록
                val offeredBlock = arrayListOf(
                    CodeBlock("move"),
                    CodeBlock("turnLeft"),
                    CodeBlock("turnRight"),
                    CodeBlock("pickAxe"),
                    CodeBlock("for", type = 1),
                    CodeBlock("}", type = 4),
                    CodeBlock("if", type = 2),
                    CodeBlock("isPickAxe", type = 3, argument = 3)
                )

                val bossBattleBlock = arrayListOf(
                    CodeBlock("if", type = 2),
                    CodeBlock("while", type = 2),
                    CodeBlock("for", type = 1),
                    CodeBlock("}", type = 4),
                    CodeBlock("attack"),
                    CodeBlock("isAlive", type = 3, argument = 7),
                    CodeBlock("detectedFire", type = 3, argument = 5),
                    CodeBlock("detectedWater", type = 3, argument = 6)
                    )

                return Stage(Map(mapList, mDrawables), Princess(), Monster(100, 0, 0), offeredBlock, bossBattleBlock)
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
//                    arrayOf(1, 0, 1),
//                    arrayOf(1, 0, 2), ......
                    arrayOf(4, "29"),
                    arrayOf(4, "34"),
                    arrayOf(4, "86")
                    )

                // 기본 제공되는 블록
                val blockButton = arrayListOf(
                    CodeBlock("move"),
                    CodeBlock("turnLeft"),
                    CodeBlock("turnRight"),
                    CodeBlock("if", type = 2),
                    CodeBlock("for", type = 1),
                    CodeBlock("isMushroom", type = 3, argument = 4),
                    CodeBlock("eatMushRoom"),
                    CodeBlock("}")
                )

                return Stage(Map(mapList, mDrawables), Princess(), Monster(0, 0, 0), blockButton)
            }

            // 2 -> {} ...

            else -> {
                return Stage(Map(), Princess(), null, arrayListOf())
            }
        }
    }
}