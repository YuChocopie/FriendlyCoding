package com.mashup.friendlycoding

import android.util.Log

//DPS : Damage Per Second 로 공격력을 의미
class Princess {
    val DPS = 10
    var isPickAxe = false
    var isBook = false
    var isMushroom = false
    var isBranch = false
    var isRock = false
    var isBat = false
    var pickAxeCnt = 0
    var mushroomCnt = 0
    var branchCnt = 0
    var bookCnt = 0
    var rockCnt = 0
    var batCnt = 0
    var killedBoss = false

    fun pickAxe(): Int {
        this.isPickAxe = true
        return ++this.pickAxeCnt
    }

    fun pickBook(): Int {
        this.isBook = true
        return ++this.bookCnt
    }

    fun eatMushroom(): Int {
        this.isMushroom = true
        return ++this.mushroomCnt
    }

    fun pickBranch(): Int {
        this.isBranch = true
        return ++this.branchCnt
    }

    fun crushRock(): Int {
        return if (isPickAxe) {
            this.isRock = true
            ++this.rockCnt
        } else {
            this.rockCnt
        }
    }
    fun killBat(): Int{
        return if (isPickAxe) {
            this.isBat = true
            ++this.batCnt
        } else {
            this.batCnt
        }
    }
    fun clear() {
        isPickAxe = false
        isBook = false
        isMushroom = false
        isBranch = false
        isRock = false
        pickAxeCnt = 0
        mushroomCnt = 0
        branchCnt = 0
        bookCnt = 0
        rockCnt = 0
        killedBoss = false
    }
}