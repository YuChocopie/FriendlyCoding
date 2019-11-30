package com.mashup.friendlycoding.model

import com.mashup.friendlycoding.Princess
import java.util.*

open class MapSettingBaseModel {
    var random = Random()
    var startX = 0
    var startY = 9

    fun rand(from: Int, to: Int): Int {
        return random.nextInt(to - from) + from
    }

    fun conditionSelector(stageNum: Int): (Princess) -> Boolean {
        when (stageNum) {
            21 -> return (fun(mPrincess: Princess): Boolean {
                return mPrincess.isBook
            })

            22 -> return (fun(mPrincess: Princess): Boolean {
                return (mPrincess.mushroomCnt == 2)
            })

            31, 33 -> return (fun(mPrincess: Princess): Boolean {
                return (mPrincess.rockCnt==GET_ROCK_COUNT)
            })

            32 -> return (fun(mPrincess: Princess): Boolean {
                return (mPrincess.batCnt == 3)
            })

            51, 52 -> return (fun(mPrincess : Princess) : Boolean {
                return mPrincess.killedBoss
            })

            else -> return (fun(_: Princess): Boolean {
                return true
            })
        }
    }

    // Act 마다 반복되는 거 있으면 알아서 빼주세용
    var defaultCodeBlock_tutorial: ArrayList<CodeBlock> = arrayListOf(
        CodeBlock("move();"),
        CodeBlock("turnLeft();"),
        CodeBlock("turnRight();")
    )
    var defaultCodeBlock: ArrayList<CodeBlock> = arrayListOf(
        CodeBlock("move();"),
        CodeBlock("turnLeft();"),
        CodeBlock("turnRight();"),
        CodeBlock("for(", type = 1),
        CodeBlock("if()", type = 2, argument = -1),
        CodeBlock("while()", type = 4, argument = -1),
        CodeBlock("}")
    )

    var stageCodeBlock0 = arrayListOf(
        CodeBlock("pickAxe();"),
        CodeBlock("isPickAxe()", type = 3, argument = IS_PICKAXE)
    )

    var stageCodeBlock2_1 = arrayListOf(
        CodeBlock("isBook()", type = 3, argument = IS_BOOK),
        CodeBlock("pickBook();")
    )
    var stageCodeBlock2_2 = arrayListOf(
        CodeBlock("isNotPoisoned()", type = 3, argument = IS_NOT_POISONED),
        CodeBlock("eatMushroom();")
    )
    var stageCodeBlock2_3 = arrayListOf(
        CodeBlock("isNotBroken()", type = 3, argument = IS_NOT_BROKEN),
        CodeBlock("pickBranch();")
    )
    var stageCodeBlock3_1 = arrayListOf(
        CodeBlock("crushRock();"),
        CodeBlock("pickAxe();")
    )
    var stageCodeBlock3_2 = arrayListOf(
        CodeBlock("pickAxe();"),
        CodeBlock("killBat();")
    )

    var stageCodeBlock3_3 = arrayListOf(
        CodeBlock("crushRock();"),
        CodeBlock("pickAxe();"),
        CodeBlock("killBat();"),
        CodeBlock("isRock();")
    )

    var stageCodeBlock4_2 = arrayListOf(
        CodeBlock("isRightLoad()", type = 3, argument = IS_RIGHT_LOAD),
        CodeBlock("isClear()", type = 3, argument = IS_CLEAR)
    )

    /**
     * 보스 배틀 코드블락
     * **/

    var defaultBattleCodeBlock = arrayListOf(
        CodeBlock("if()", type = 2, argument = -1),
        CodeBlock("while()", type = 4, argument = -1),
        CodeBlock("for(", type = 1, argument = -1),
        CodeBlock("}"),
        CodeBlock("attack();"),
        CodeBlock("isAlive()", type = 3, argument = IS_ALIVE)
    )

    var battleCodeBlock1 = arrayListOf(
        CodeBlock("detectedFire()", type = 3, argument = 0),
        CodeBlock("detectedWater()", type = 3, argument = 1),
        CodeBlock("iceShield();"),
        CodeBlock("fireShield();")
    )

    var battleCodeBlock2 = arrayListOf(
        CodeBlock("bossJumped()", type = 3, argument = BOSS_JUMPED),
        CodeBlock("bossPunch()", type = 3, argument = BOSS_PUNCH),
        CodeBlock("bossFistMoved()", type = 3, argument = BOSS_FIST_MOVED),
        CodeBlock("bossFistDown()", type = 3, argument = BOSS_FIST_DOWN),
        CodeBlock("wait();"),
        CodeBlock("jump();"),
        CodeBlock("dodge();")
    )

    var battleCodeBlock3 = arrayListOf(
        CodeBlock("bossBlackhole()", type = 3, argument = BOSS_BLACKHOLE),
        CodeBlock("isBlackhole()", type = 3, argument = IS_BLACKHOLE),
        CodeBlock("bossGreenHand()", type = 3, argument = BOSS_GREENHAND),
        CodeBlock("grabTight();"),
        CodeBlock("readySpell();", type = 5),
        CodeBlock("wandSpell();"),
        CodeBlock("shoutSpell();"),
        CodeBlock("attack", argument = ATTACK),
        CodeBlock("shield", argument = SHIELD)
        )

    val mapListActNull = arrayOf(
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    )

    var mapListAct0 = arrayOf(
        arrayOf(0, 0, 0, 0, 1, 0, 1, 0, 0, 0),
        arrayOf(1, 1, 0, 0, 1, 0, 1, 1, 1, 1),
        arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0),
        arrayOf(1, 0, 1, 1, 1, 0, 1, 1, 1, 1),
        arrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(1, 0, 0, 0, 0, 1, 1, 1, 1, 0),
        arrayOf(1, 1, 1, 1, 0, 1, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 1, 0, 1, 0, 0),
        arrayOf(0, 1, 1, 1, 1, 1, 0, 1, 1, 1),
        arrayOf(0, 0, 2, 0, 0, 1, 0, 0, 0, 2)    // 클리어되는 곳을 일단 2로 설정함, 3은 곡괭이
    )
    var mapListAct1 = arrayOf(
        arrayOf(0, 0, 0, 0, 1, 0, 1, 0, 0, 0),
        arrayOf(1, 1, 0, 0, 1, 0, 1, 1, 1, 1),
        arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0),
        arrayOf(1, 0, 1, 1, 1, 0, 1, 1, 1, 1),
        arrayOf(1, 0, 0, 4, 0, 0, 0, 0, 0, 0),
        arrayOf(1, 0, 0, 0, 0, 1, 1, 1, 1, 0),
        arrayOf(1, 1, 1, 1, 1, 1, 0, 0, 4, 0),
        arrayOf(0, 0, 0, 0, 2, 1, 0, 1, 0, 0),
        arrayOf(0, 1, 1, 1, 1, 1, 0, 1, 1, 1),
        arrayOf(0, 0, 0, 0, 2, 1, 0, 0, 0, 2)  // 4는 버섯
    )

    var mapListAct2 = arrayOf(
        arrayOf(1, 1, 0, 0, 0, 1, 1, 1, 1, 1),
        arrayOf(1, 1, 0, 0, 0, 1, 1, 1, 1, 1),
        arrayOf(1, 1, 0, 0, 0, 0, 1, 1, 1, 1),
        arrayOf(1, 1, 0, 0, 0, 0, 0, 0, 0, 1),
        arrayOf(1, 1, 0, 0, 0, 0, 0, 0, 0, 1),
        arrayOf(1, 1, 0, 0, 0, 0, 0, 0, 0, 1),
        arrayOf(1, 1, 0, 0, 0, 0, 0, 0, 0, 1),
        arrayOf(1, 1, 0, 0, 0, 0, 0, 0, 0, 1),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    )
    var mapListAct3 = arrayOf(
        arrayOf(1, 1, 1, 1, 1, 0, 0, 0, 0, 0),
        arrayOf(1, 1, 1, 1, 1, 0, 0, 0, 0, 0),
        arrayOf(1, 1, 1, 1, 1, 0, 0, 0, 0, 0),
        arrayOf(1, 1, 1, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 1, 1),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 1, 1, 1),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 1, 1, 1),
        arrayOf(0, 0, 0, 0, 0, 0, 1, 1, 1, 1),
        arrayOf(0, 0, 0, 0, 0, 0, 1, 1, 1, 1)
    )
    var mapListAct4_1 = arrayOf(
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
        arrayOf(0, 0, 0, 0, 0, 2, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    )
    var mapListAct4_2 = arrayOf(
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 1, 1, 1, 1, 1, 1, 1, 1, 0),
        arrayOf(0, 1, 0, 0, 0, 0, 0, 0, 1, 0),
        arrayOf(0, 1, 0, 1, 1, 1, 1, 0, 1, 0),
        arrayOf(0, 1, 0, 1, 2, 1, 1, 0, 1, 0),
        arrayOf(0, 1, 0, 1, 0, 1, 1, 0, 1, 0),
        arrayOf(0, 1, 0, 1, 0, 1, 1, 0, 1, 0),
        arrayOf(0, 1, 0, 1, 0, 0, 0, 0, 1, 0),
        arrayOf(0, 1, 0, 1, 1, 1, 1, 1, 1, 0),
        arrayOf(0, 1, 0, 0, 0, 0, 0, 0, 0, 0)
    )
}