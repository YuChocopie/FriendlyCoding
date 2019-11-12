package com.mashup.friendlycoding.model

import android.util.Log
import com.mashup.friendlycoding.Map
import com.mashup.friendlycoding.Monster
import com.mashup.friendlycoding.Princess
import com.mashup.friendlycoding.R

class MapSettingModel : MapSettingBaseModel() {

    // Act 마다 반복되는 거 있으면 알아서 빼주세용
    fun getStageInfo(stageNum: Int): Stage {
        Log.e("stageNum", "$stageNum")
        when (stageNum / 10) {
            // Act 1
            0 -> {
                // 맵 정보
                // TODO : 아이템의 위치 랜덤하게
                val mapList = mapListAct0
                // 드로어블
                val mDrawables = MapDrawable(backgroundImg = R.drawable.bg_stage00)
                mDrawables.monsterImg = R.drawable.monster
                mDrawables.bossBattleBackgroundImg = R.drawable.demonic_castle
                mDrawables.itemImg = arrayListOf(
                    arrayOf(3, "i29") // 순서대로 아이템의 종류, 아이템의 xy좌표 -> ixy는 아이템 이미지 뷰의 아이디
                )

                // 기본 제공되는 블록
                stageCodeBlock0.addAll(defaultCodeBlock)
                battleCodeBlock0.addAll(defaultBattleCodeBlock)

                return Stage(
                    Map(mapList, mDrawables),
                    Princess(),
                    Monster(1, 100, 0, 0),
                    stageCodeBlock0,
                    battleCodeBlock0
                )
            }

            1 -> {
                val mapList = mapListAct1
                // 드로어블
                val mDrawables = MapDrawable(backgroundImg = R.drawable.bg_stage01)
                mDrawables.monsterImg = R.drawable.monster
                mDrawables.itemImg = arrayListOf(
                    arrayOf(4, "i29"),
                    arrayOf(4, "i34"),
                    arrayOf(4, "i86")
                )

                // 기본 제공되는 블록
                return Stage(
                    Map(mapList, mDrawables),
                    Princess(),
                    Monster(1, 100, 0, 0),
                    defaultCodeBlock_tutorial
                )
            }

            2 -> {
                val mDrawables = MapDrawable(backgroundImg = R.drawable.bg_stage02)
                var mapList: Array<Array<Int>> = mapListActNull
                when (stageNum % 10) {
                    1 -> {
                        mapList = mapListAct2_1
                        mDrawables.itemImg = arrayListOf(
                            arrayOf(5, "i41")
                        )
                        defaultCodeBlock.addAll(stageCodeBlock2_1)
                    }

                    2 -> {
                        mapList = mapListAct2_2

                        mDrawables.itemImg = arrayListOf(
                            arrayOf(4, "i41"),
                            arrayOf(4, "i23")
                        )//독버섯은 랜덤생성

                        defaultCodeBlock.addAll(stageCodeBlock2_2)
                    }

                    3 -> {
                        mapList = mapListAct2_3

                        mDrawables.itemImg = arrayListOf(
                            arrayOf(6, "i23"),
                            arrayOf(6, "i19")
                        )
                        defaultCodeBlock.addAll(stageCodeBlock2_3)
                    }
                }
                return Stage(
                    Map(mapList, mDrawables),
                    Princess(),
                    Monster(1, 100, 0, 0),
                    defaultCodeBlock
                )
            }

            else -> {
                return Stage(
                    Map(),
                    Princess(), null, arrayListOf())
            }
        }
    }
}