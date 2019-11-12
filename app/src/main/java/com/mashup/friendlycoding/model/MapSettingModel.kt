package com.mashup.friendlycoding.model

import android.util.Log
import android.view.View
import com.mashup.friendlycoding.Map
import com.mashup.friendlycoding.Monster
import com.mashup.friendlycoding.Princess
import com.mashup.friendlycoding.R

class MapSettingModel : MapSettingBaseModel() {
    // Act 마다 반복되는 거 있으면 알아서 빼주세용

    fun setMapRandimItem(mapList: Array<Array<Int>>, mDrawables: MapDrawable): Map {
        var map = mapList
        for (i in 0 until mDrawables.item.size) {
            while (true) {
                val a = rand(0, 10)
                val b = rand(0, 10)
                if (map[a][b] == 0) {
                    map[a][b] = mDrawables.item[i].item_id
                    mDrawables.item[i].X = a
                    mDrawables.item[i].Y = b
                    mDrawables.item[i].visibility = View.VISIBLE
                    break
                }
            }
        }
        return Map(mapList, mDrawables)

    }
    fun getStageInfo(stageNum: Int): Stage {
        when (stageNum / 10) {
            // Act 1
            0 -> {
                // TODO : 아이템의 위치 랜덤하게
                val mapList = mapListAct0
                // 드로어블
                val mDrawables = MapDrawable(backgroundImg = R.drawable.bg_stage00)
                mDrawables.monsterImg = R.drawable.monster
                mDrawables.bossBattleBackgroundImg = R.drawable.demonic_castle
                // 기본 제공되는 블록
                stageCodeBlock0.addAll(defaultCodeBlock)
                battleCodeBlock0.addAll(defaultBattleCodeBlock)

                return Stage(
                    setMapRandimItem(mapList, mDrawables),
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
                mDrawables.item = arrayListOf(
                    MapItem(R.drawable.ic_mushroom, 29),
                    MapItem(R.drawable.ic_sunny, 34),
                    MapItem(R.drawable.ic_mushroom_poison, 86)
                )
                battleCodeBlock0.addAll(defaultBattleCodeBlock)


                // 기본 제공되는 블록
                return Stage(
                    setMapRandimItem(mapList, mDrawables),
                    Princess(),
                    Monster(1, 100, 0, 0),
                    defaultCodeBlock_tutorial,
                    battleCodeBlock0
                )
            }

            2 -> {
                val mDrawables = MapDrawable(backgroundImg = R.drawable.bg_stage02)
                var mapList: Array<Array<Int>> = mapListActNull
                when (stageNum % 10) {
                    1 -> {
                        mapList = mapListAct2_1
                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.ic_mushroom, 41)
                        )
                        defaultCodeBlock.addAll(stageCodeBlock2_1)
                    }

                    2 -> {
                        mapList = mapListAct2_2

                        mapList = mapListAct2_1
                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.ic_mushroom, 41),
                            MapItem(R.drawable.ic_mushroom_poison, 23)
                        )

                        defaultCodeBlock.addAll(stageCodeBlock2_2)
                    }

                    3 -> {
                        mapList = mapListAct2_3

                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.ic_mushroom, 41),
                            MapItem(R.drawable.ic_mushroom_poison, 19)
                        )
                        defaultCodeBlock.addAll(stageCodeBlock2_3)
                    }
                }
                return Stage(
                    setMapRandimItem(mapList, mDrawables),
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