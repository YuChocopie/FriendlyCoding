package com.mashup.friendlycoding.model

import android.util.Log
import android.view.View
import com.mashup.friendlycoding.Map
import com.mashup.friendlycoding.Monster
import com.mashup.friendlycoding.Princess
import com.mashup.friendlycoding.R

class MapSettingModel : MapSettingBaseModel() {
    private fun setMapRandimItem(mapList: Array<Array<Int>>, mDrawables: MapDrawable): Map {
        for (i in 0 until mDrawables.item.size) {
            while (true) {
                val a = rand(0, 10)
                val b = rand(0, 10)
                if (mapList[a][b] == 0) {
                    mapList[a][b] = (i+1) * 10 + mDrawables.item[i].item_id
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
        Log.e("stageNum", "$stageNum")

        when (stageNum / 10) {
            // Act 0
            0 -> {
                // 맵 정보
                // TODO : 아이템의 위치 랜덤하게
                val mapList = mapListAct0
                // 드로어블
                val mDrawables =
                    MapDrawable(backgroundImg = R.drawable.bg_stage00, princessX = 0, princessY = 9)
                mDrawables.monsterImg = R.drawable.monster
                mDrawables.bossBattleBackgroundImg = R.drawable.demonic_castle
                // 기본 제공되는 블록
                stageCodeBlock0.addAll(defaultCodeBlock)
                battleCodeBlock1.addAll(defaultBattleCodeBlock)

                return Stage(
                    setMapRandimItem(mapList, mDrawables),
                    Princess(),
                    Monster(1, 100, 0, 0),
                    stageCodeBlock0,
                    battleCodeBlock1,
                    conditionSelector(stageNum)
                )
            }

            // Act 1
            1 -> {
                val mapList = mapListAct1
                // 드로어블
                val mDrawables = MapDrawable(backgroundImg = R.drawable.bg_stage01, princessX = 0, princessY = 9)
                mDrawables.monsterImg = R.drawable.monster1
                mDrawables.bossBattleBackgroundImg = R.drawable.demonic_castle
                mDrawables.item = arrayListOf(
                    MapItem(R.drawable.ic_sunny, 34)
                )
                battleCodeBlock1.addAll(defaultBattleCodeBlock)

                when (stageNum % 10) {
                    // Stage 1
                    1 -> {
                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.circle, 2),
                            MapItem(R.drawable.circle, 2)
                        )
                        mDrawables.princessX = 0
                        mDrawables.princessY = 9
                        mapList[9][4] = mDrawables.item[0].item_id
                        mDrawables.item[0].X = 9
                        mDrawables.item[0].Y = 4
                        mDrawables.item[0].visibility = View.VISIBLE
                    }

                    2 -> {
                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.circle, 2),
                            MapItem(R.drawable.circle, 2)
                        )
                        mDrawables.princessX = 0
                        mDrawables.princessY = 9
                        mapList[7][4] = mDrawables.item[0].item_id
                        mapList[0][0] = mDrawables.item[1].item_id
                        mDrawables.item[0].X = 7
                        mDrawables.item[0].Y = 4
                        mDrawables.item[0].visibility = View.VISIBLE
                        mDrawables.item[1].X = 0
                        mDrawables.item[1].Y = 0
                        mDrawables.item[1].visibility = View.VISIBLE
                    }

                    3 -> {
                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.circle, 2),
                            MapItem(R.drawable.ic_sunny, 2)
                        )
                        mDrawables.princessX = 0
                        mDrawables.princessY = 0
                        startX = 0
                        startY = 0
                        mapList[0][0] = mDrawables.item[1].item_id
                        mDrawables.item[0].X = 0
                        mDrawables.item[0].Y = 0
                        mDrawables.item[0].visibility = View.VISIBLE
                    }
                }

                // 기본 제공되는 블록
                return Stage(
                    Map(mapList, mDrawables, startX, startY),
                    Princess(),
                    Monster(1, 10, 0, 0),
                    defaultCodeBlock_tutorial,
                    battleCodeBlock1,
                    conditionSelector(stageNum),
                    princessAction = arrayListOf(R.drawable.fire_shield, R.drawable.ice_shield)
                )
            }

            2 -> {
                val mDrawables = MapDrawable(backgroundImg = R.drawable.bg_stage02)
                var mapList: Array<Array<Int>> = mapListActNull
                var princessAction : ArrayList<Int>? = null
                defaultBattleCodeBlock.addAll(battleCodeBlock1)
                when (stageNum % 10) {
                    1 -> {
                        mapList = mapListAct2
                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.circle, 2),
                            MapItem(R.drawable.ic_book, 5)
                        )
                        defaultCodeBlock.addAll(stageCodeBlock2_1)
                        mDrawables.item[0].X = 9
                        mDrawables.item[0].Y = 4
                        mDrawables.item[0].visibility = View.VISIBLE
                    }

                    2 -> {
                        mapList = mapListAct2
                        princessAction = arrayListOf(R.drawable.fire_shield, R.drawable.ice_shield)
                        mDrawables.monsterImg = R.drawable.monster1
                        mDrawables.bossBattleBackgroundImg = R.drawable.demonic_castle
                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.circle, 2),
                            MapItem(R.drawable.monster1, 7),
                            MapItem(R.drawable.ic_mushroom, 4),
                            MapItem(R.drawable.ic_mushroom, 4),
                            MapItem(R.drawable.ic_mushroom_poison, 8)
                        )
                        defaultCodeBlock.addAll(stageCodeBlock2_2)
                    }

                    3 -> {
                        mapList = mapListAct2

                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.circle, 2),
                            MapItem(R.drawable.ic_branch, 6),
                            MapItem(R.drawable.ic_branch, 6)
                        )
                        defaultCodeBlock.addAll(stageCodeBlock2_3)
                    }
                }

                return Stage(
                    setMapRandimItem(mapList, mDrawables),
                    Princess(),
                    Monster(1, 10, 0, 0),
                    defaultCodeBlock,
                    defaultBattleCodeBlock,
                    conditionSelector(stageNum),
                    princessAction = princessAction
                )
            }

            3 -> {
                val mDrawables = MapDrawable(backgroundImg = R.drawable.bg_stage02)
                var mapList: Array<Array<Int>> = mapListActNull
                var princessAction : ArrayList<Int>? = null

                return Stage(
                    setMapRandimItem(mapList, mDrawables),
                    Princess(),
                    Monster(1, 10, 0, 0),
                    defaultCodeBlock,
                    defaultBattleCodeBlock,
                    conditionSelector(stageNum),
                    princessAction = princessAction
                )
            }

            4 -> {
                val mDrawables = MapDrawable(backgroundImg = R.drawable.bg_stage02)
                var mapList: Array<Array<Int>> = mapListActNull
                var princessAction : ArrayList<Int>? = null

                return Stage(
                    setMapRandimItem(mapList, mDrawables),
                    Princess(),
                    Monster(1, 10, 0, 0),
                    defaultCodeBlock,
                    defaultBattleCodeBlock,
                    conditionSelector(stageNum),
                    princessAction = princessAction
                )
            }

            5 -> {
                val mDrawables = MapDrawable(backgroundImg = R.drawable.bg_stage02)
                val mapList: Array<Array<Int>> = mapListActNull
                val princessAction = arrayListOf(
                    R.drawable.fire_shield,
                    R.drawable.ice_shield
                )

                val bossAction : ArrayList<Int>? = arrayListOf(
                    R.drawable.attack_fire,
                    R.drawable.attack_ice,
                    0,
                    R.drawable.fistmoved,
                    R.drawable.monster2,
                    R.drawable.punch,
                    R.drawable.blackhole,
                    R.drawable.monster3_attack
                    )

                var type = 2
                when (stageNum % 10) {
                    1 -> {
                        mDrawables.monsterImg = R.drawable.monster2
                        mDrawables.bossBattleBackgroundImg = R.drawable.demonic_castle // TODO : 배경 변경 유정이 화이팅!!^^
                        mDrawables.item = arrayListOf(MapItem(R.drawable.monster2, 7))
                        defaultBattleCodeBlock.addAll(battleCodeBlock2)
                        type = 2
                    }

                    2 -> {
                        mDrawables.monsterImg = R.drawable.monster3
                        mDrawables.bossBattleBackgroundImg = R.drawable.demonic_castle // TODO : 배경 변경 유정이 화이팅!!^^
                        mDrawables.item = arrayListOf(MapItem(R.drawable.monster3, 7))
                        defaultBattleCodeBlock.addAll(battleCodeBlock3)
                        type = 3
                    }
                }

                return Stage(
                    setMapRandimItem(mapList, mDrawables),
                    Princess(),
                    Monster(type, 10, 0, 0),
                    defaultCodeBlock,
                    defaultBattleCodeBlock,
                    conditionSelector(stageNum),
                    princessAction = princessAction,
                    bossAction = bossAction
                )
            }

            else -> {
                return Stage(Map(), Princess(), null, arrayListOf(), null, conditionSelector(stageNum))
            }
        }
    }
}