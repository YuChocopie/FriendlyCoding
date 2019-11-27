package com.mashup.friendlycoding.model

import android.util.Log
import com.mashup.friendlycoding.ignoreBlanks

class BossBattleRunModel : RunBaseModel() {
//    fun defenseSuccess(attackType: Int) : Boolean {
//        when (attackType) {
//            BOSS_FIRE_ATTACK -> {
//                return if (mCodeBlock.value!!.size > IR+1) {
//                    (ignoreBlanks(mCodeBlock.value!![IR+1].funcName) == "iceShield();")
//                } else
//                    false
//            }
//
//            BOSS_WATER_ATTACK -> {
//                return if (mCodeBlock.value!!.size > IR+1) {
//                    (ignoreBlanks(mCodeBlock.value!![IR+1].funcName) == "fireShield();")
//                } else
//                    false
//            }
//
//            BOSS_JUMPED -> {
//                Log.e("현재 코드 블록", "${this.mCodeBlock.value}")
//                return if (this.mCodeBlock.value!!.size > IR+2) {
//                    (ignoreBlanks(this.mCodeBlock.value!![IR+2].funcName) == "jump();")
//                } else
//                    false
//            }
//
//            BOSS_FIST_MOVED -> {
//                return true
//            }
//
//            BOSS_FIST_DOWN -> {
//                return true
//            }
//
//            BOSS_PUNCH -> {
//                Log.e("현재 코드 블록", "${this.mCodeBlock.value!!.size}")
//                return if (this.mCodeBlock.value!!.size > IR+1) {
//                    (ignoreBlanks(mCodeBlock.value!![IR+1].funcName) == "dodge();")
//                } else
//                    false
//            }
//
//            BOSS_BLACKHOLE -> {
//                return true
//            }
//
//            BOSS_GREENHAND -> {
//                return true
//            }
//
//            else -> {
//                return true
//            }
//        }
//    }
}