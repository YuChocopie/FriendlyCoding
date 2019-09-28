package com.mashup.friendlycoding.model

class RunBattle {
    private val monster = Monster(100)
    private val princess = Princess(10)

    fun runBattle () {
        while (monster.currentHP > 10) {
            //TODO : Thread 혹은 Handler로 턴제 게임 구현하기
        }
    }
}