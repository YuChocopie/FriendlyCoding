package com.mashup.friendlycoding.model

import android.util.Log
import android.view.View
import com.mashup.friendlycoding.Monster
import com.mashup.friendlycoding.Princess
import com.mashup.friendlycoding.R

class MapSettingModel : MapSettingBaseModel() {
    private fun setMapRandomItem(mapList: Array<Array<Int>>, mDrawables: MapDrawable): Map {
        for (i in 0 until mDrawables.item.size) {
            while (true) {
                val a = rand(0, 10)
                val b = rand(0, 10)
                if (mapList[a][b] == 0) {
                    mapList[a][b] = (i + 1) * BASE + mDrawables.item[i].item_id
                    mDrawables.item[i].X = a
                    mDrawables.item[i].Y = b
                    mDrawables.item[i].visibility = View.VISIBLE
                    break
                }
            }
        }
        return Map(mapList, mapList, mDrawables)
    }

    private fun setMapItem(
        arr: Array<Array<Int>>,
        mapList: Array<Array<Int>>,
        mDrawables: MapDrawable
    ) {
        for (i in 0 until mDrawables.item.size) {
            for (j in 0 until mDrawables.item.size - 1) {
                var x = arr[i][j]
                var y = arr[i][j + 1]
                mapList[x][y] = (i + 1) * BASE + mDrawables.item[i].item_id
                mDrawables.item[i].X = x
                mDrawables.item[i].Y = y
                mDrawables.item[i].visibility = View.VISIBLE

            }

        }
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
                mDrawables.bossBattleBackgroundImg = R.drawable.bg_boss_demonic_castle
                // 기본 제공되는 블록
                stageCodeBlock0.addAll(defaultCodeBlock)
                battleCodeBlock1.addAll(defaultBattleCodeBlock)

                return Stage(
                    setMapRandomItem(mapList, mDrawables),
                    Princess(),
                    stageCodeBlock0,
                    conditionSelector(stageNum)
                )
            }

            // Act 1
            1 -> {
                val mapList = mapListAct1
                // 드로어블
                val mDrawables = MapDrawable(backgroundImg = R.drawable.bg_stage01, princessX = 0, princessY = 9)

                mDrawables.item = arrayListOf(
                    MapItem(R.drawable.ic_sunny, 34)
                )
                battleCodeBlock1.addAll(defaultBattleCodeBlock)

                when (stageNum % 10) {
                    // Stage 1
                    1 -> {
                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.ic_circle, CLEAR)
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
                            MapItem(R.drawable.ic_circle, CLEAR),
                            MapItem(R.drawable.ic_circle, CLEAR)
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
                            MapItem(R.drawable.ic_circle, CLEAR),
                            MapItem(R.drawable.ic_circle, CLEAR)
                        )
                        mDrawables.princessX = 0
                        mDrawables.princessY = 0
                        startX = 0
                        startY = 0
                        mapList[9][9] = mDrawables.item[1].item_id
                        mDrawables.item[0].X = 0
                        mDrawables.item[0].Y = 0
                        mDrawables.item[1].X = 9
                        mDrawables.item[1].Y = 9
                    }
                }

                // 기본 제공되는 블록
                return Stage(
                        Map(mapList, mapList, mDrawables, startX, startY),
                        Princess(),
                        offeredBlock = defaultCodeBlock_tutorial,
                        clearCondition = conditionSelector(stageNum)
                        )
            }

            2 -> {
                val mDrawables = MapDrawable(backgroundImg = R.drawable.bg_stage02)
                var mapList: Array<Array<Int>> = mapListAct2
                defaultCodeBlock.removeAt(3)
                defaultCodeBlock.removeAt(4)
                when (stageNum % 10) {
                    1 -> {
                        mapList = mapListAct2
                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.ic_circle, CLEAR),
                            MapItem(R.drawable.ic_book, BOOK)
                        )
                        defaultCodeBlock.addAll(stageCodeBlock2_1)
                        mDrawables.item[0].X = 9
                        mDrawables.item[0].Y = 4
                        mDrawables.item[0].visibility = View.VISIBLE
                    }

                    2 -> {
                        mapList = mapListAct2
                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.ic_circle, CLEAR),
                            MapItem(R.drawable.ic_mushroom, MUSHROOM),
                            MapItem(R.drawable.ic_mushroom, MUSHROOM),
                            MapItem(R.drawable.ic_mushroom, POISONMUSHROOM)
                        )
                        defaultCodeBlock.addAll(stageCodeBlock2_2)
                    }

                    3 -> {
                        mapList = mapListAct2
                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.oldman, CLEAR),
                            MapItem(R.drawable.ic_branch, BRANCH),
                            MapItem(R.drawable.ic_branch, BRANCH),
                            MapItem(R.drawable.ic_branch, BROKEN_BRANCH)
                        )
                        defaultCodeBlock.addAll(stageCodeBlock2_3)
                    }
                }

                return Stage(
                    setMapRandomItem(mapList, mDrawables),
                    Princess(),
                    defaultCodeBlock,
                    conditionSelector(stageNum)
                )
            }

            3 -> {
                val mDrawables = MapDrawable(backgroundImg = R.drawable.bg_stage03)//배경
                var mapList: Array<Array<Int>> = mapListActNull
                defaultBattleCodeBlock.addAll(battleCodeBlock1)
                when (stageNum % 10) {
                    1 -> {
                        mapList = mapListAct31//테스트
                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.ic_crystal_blue, ROCK),
                            MapItem(R.drawable.ic_pick_axe, PICKAXE)
                        )


                        val arr = arrayOf(//넣고싶은 위치선택
                            arrayOf(9, 3),
                            arrayOf(9, 1)
                        )
                        setMapItem(arr, mapList, mDrawables)



                        defaultCodeBlock.addAll(stageCodeBlock3_1)

                    }

                    2 -> {
                        mapList = mapListAct2
                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.ic_circle, CLEAR),
                            MapItem(R.drawable.ic_mushroom, MUSHROOM),
                            MapItem(R.drawable.ic_mushroom, MUSHROOM)
                        )
                        defaultCodeBlock.addAll(stageCodeBlock2_2)
                    }

                    3 -> {
                        mapList = mapListAct2

                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.ic_circle, CLEAR),
                            MapItem(R.drawable.ic_branch, BRANCH),
                            MapItem(R.drawable.ic_branch, BROKEN_BRANCH),
                            MapItem(R.drawable.ic_branch, BRANCH)
                        )
                        defaultCodeBlock.addAll(stageCodeBlock2_3)
                    }
                }

                return Stage(
                    Map(mapList, mapList, mDrawables, startX, startY),
                    Princess(),
                    defaultCodeBlock,
                    conditionSelector(stageNum)
                )
            }

            4 -> {
                val mDrawables = MapDrawable()
                var mapList: Array<Array<Int>> = mapListActNull
                var princessAction : ArrayList<Int>? = arrayListOf(
                    R.drawable.attack_fire_shield,
                    R.drawable.attack_ice_shield)

                val bossAction : ArrayList<Int>? = arrayListOf(
                    R.drawable.attack_fire,
                    R.drawable.attack_ice)


                when (stageNum % 10) {
                    // Stage 1
                    1 -> {
                        mDrawables.backgroundImg = R.drawable.bg_stage04_1
                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.ic_circle, CLEAR)
                        )
                        mDrawables.princessX = 4
                        mDrawables.princessY = 9
                    }

                    2 -> {
                        mDrawables.backgroundImg = R.drawable.bg_stage04_2

                        mDrawables.item = arrayListOf(
                            MapItem(R.drawable.ic_circle, CLEAR),
                            MapItem(R.drawable.ic_circle, CLEAR)
                        )
                        mDrawables.princessX = 0
                        mDrawables.princessY = 9

                    }
                }

                    return Stage(
                        setMapRandomItem(mapList, mDrawables),
                        Princess(),
                        defaultCodeBlock,
                        conditionSelector(stageNum),
                        Monster(1, 100, 0, 0),
                        defaultBattleCodeBlock,
                        princessAction,
                        bossAction
                        )

            }

            5 -> {
                val mDrawables = MapDrawable(backgroundImg = R.drawable.bg_stage05)
                var mapList: Array<Array<Int>> = mapListActNull
                val princessAction = arrayListOf(
                    R.drawable.attack_fire_shield,
                    R.drawable.attack_ice_shield,
                    R.drawable.wand,
                    R.drawable.wand,
                    R.drawable.attackspell,
                    R.drawable.shieldspell
                )
                mapList = mapListAct2

                defaultCodeBlock.add(
                    CodeBlock("fightBoss();")
                )

                val bossAction: ArrayList<Int>? = arrayListOf(
                    R.drawable.attack_fire,
                    R.drawable.attack_ice,
                    R.drawable.jump,
                    R.drawable.monster2_fistmoved,
                    R.drawable.monster2,
                    R.drawable.monster2_punch,
                    R.drawable.ic_blackhole,
                    R.drawable.monster3_attack
                )

                var type = 2
                when (stageNum % 10) {
                    1 -> {
                        mDrawables.monsterImg = R.drawable.monster2
                        mDrawables.bossBattleBackgroundImg = R.drawable.bg_boss_demonic_castle
                        mDrawables.item.addAll(
                            arrayListOf(
                                MapItem(R.drawable.monster2, BOSS),
                                MapItem(R.drawable.ic_circle, CLEAR)
                            )
                        )
                        defaultBattleCodeBlock.addAll(battleCodeBlock2)
                        type = 2
                    }

                    2 -> {
                        mDrawables.monsterImg = R.drawable.monster3
                        mDrawables.bossBattleBackgroundImg = R.drawable.bg_boss_demonic_castle
                        mDrawables.item.addAll(
                            arrayListOf(
                                MapItem(R.drawable.monster3, BOSS),
                                MapItem(R.drawable.ic_circle, CLEAR)
                            )
                        )
                        defaultBattleCodeBlock.addAll(battleCodeBlock3)
                        type = 3
                    }
                }

                mDrawables.princessX = 5
                mDrawables.princessY = 9
                mapList[3][5] = mDrawables.item[0].item_id
                mapList[9][4] = mDrawables.item[1].item_id
                mDrawables.item[0].X = 3
                mDrawables.item[0].Y = 5
                mDrawables.item[0].visibility = View.VISIBLE
                mDrawables.item[1].X = 9
                mDrawables.item[1].Y = 4
                mDrawables.item[1].visibility = View.VISIBLE

                return Stage(
                    Map(mapList, mapList, mDrawables, startX, startY),
                    Princess(),
                    defaultCodeBlock,
                    conditionSelector(stageNum),
                    Monster(type, 50, 0, 0),
                    defaultBattleCodeBlock,
                    princessAction,
                    bossAction
                )
            }

            else -> {
                return Stage(
                    Map(),
                    Princess(),
                    arrayListOf(),
                    conditionSelector(stageNum))
            }
        }
    }
}