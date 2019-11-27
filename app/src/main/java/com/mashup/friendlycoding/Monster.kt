package com.mashup.friendlycoding

import java.util.*

class Monster(val type: Int, private val maxHP: Int, val x: Int=0, val y: Int=0) {
    private var hp = this.maxHP  // 보스의 현재 체력
    private var rand = Random()  // 보스가 공격을 할 지 말 지, 혹은 어떤 공격을 할 지 정하는 난수
    private var det = 0   // rand에서 뽑힌 수
    var attackType = -1

    fun monsterAttacked(DPS: Int) {
        this.hp -= DPS
    }

    fun isAlive(): Boolean {
        return this.hp > 0
    }

    fun getHP(): Int {
        return this.hp
    }

    fun attack() {
        when (this.type) {
            // 유형에 따라 달라지는 공격 빈도
            1 -> {
                det = rand.nextInt(5)
                if (det == 1) {   // 1/3 확률
                    attackType = rand.nextInt(2)  // 0, 1
                } else
                    attackType = -1
            }

            2 -> {
                det = rand.nextInt(5)
                if (det == 1) {   // 1/3 확률
                    attackType = rand.nextInt(2) + 2  // 2, 3
                } else
                    attackType = -1
            }

            3 -> {
                det = rand.nextInt(5)
                if (det == 1) {   // 1/3 확률
                    attackType = rand.nextInt(2) + 6
                } else
                    attackType = -1
            }
        }
    }
}