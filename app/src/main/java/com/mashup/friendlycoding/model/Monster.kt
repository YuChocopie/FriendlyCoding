package com.mashup.friendlycoding.model

class Monster (val type : Int, private val maxHP : Int, val x : Int, val y : Int) {
    private var hp = this.maxHP

    fun monsterAttacked(DPS : Int) {
        this.hp -= DPS
    }

    fun isAlive () : Boolean {
        return this.hp > 0
    }

    fun getHP () : Int {
        return this.hp
    }
}