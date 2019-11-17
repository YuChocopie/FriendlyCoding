package com.mashup.friendlycoding

//DPS : Damage Per Second 로 공격력을 의미
class Princess {
    val DPS = 10
    var isPickAxe = false
    var isBook = false
    var isMushroom = false
    var isBranch = false
    var mushroomCnt = 0
    var branchCnt = 0
    var bookCnt = 0

    fun pickAxe() {
        this.isPickAxe = true
    }

    fun pickBook() {
        this.bookCnt++
        this.isBook = true
    }

    fun eatMushroom() {
        this.mushroomCnt++
        this.isMushroom = true
    }

    fun pickBranch() {
        this.branchCnt++
        this.isBranch = true
    }
}