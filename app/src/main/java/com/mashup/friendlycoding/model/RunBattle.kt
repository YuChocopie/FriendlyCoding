package com.mashup.friendlycoding.model

import androidx.lifecycle.MutableLiveData
import com.mashup.friendlycoding.adapter.HP
import com.mashup.friendlycoding.viewmodel.CodeBlock

class RunBattle {
    private val monster = Monster(100)
    private val princess = Princess(10)
    private var mCodeBlock = MutableLiveData<ArrayList<CodeBlock>>()


    fun runBattle () {
    }

    fun monsterAttacked () {
        monster.currentHP -= 10
    }

    fun princessAttacked () {
        princess
    }
}