package com.mashup.friendlycoding

//DPS : Damage Per Second 로 공격력을 의미
class Princess {
    val DPS = 10
    var isPickAxe = false
    private var mushroomCnt = 0

    fun pickAxe() {
        this.isPickAxe = true
    }

    fun eatMushroom () {
        this.mushroomCnt++
    }
}