package com.mashup.friendlycoding.viewmodel

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.friendlycoding.adapter.HP
import com.mashup.friendlycoding.model.Run
import com.mashup.friendlycoding.model.RunBattle
import kotlin.collections.ArrayList

class BattleViewModel {
    private var mRunBattle = RunBattle()
    private var mFireImg : ImageView? = null


    fun monsterAttack (attackType : Int) {
        if (attackType == 0) { // FIRE
            //TODO : 파이어볼이 나가는 이미지를 생성 ... Layout과 Context를 View로부터 받아야 하는데, 어떻게?
        }

        else if (attackType == 1) { // EARTH
        }
    }

    fun monsterAttacked () {
        mRunBattle.monsterAttacked()
    }

    fun runBattle(view : View) {
        Log.e("배틀", "실행")
        mRunBattle.runBattle()
    }
}