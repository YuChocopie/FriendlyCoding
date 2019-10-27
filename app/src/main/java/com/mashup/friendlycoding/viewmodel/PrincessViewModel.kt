package com.mashup.friendlycoding.viewmodel

import android.os.Handler
import android.widget.ImageView
import androidx.lifecycle.ViewModel

class PrincessViewModel : ViewModel() {
    //0:go 1:left 2:right
    val move = listOf<Int>(0, 2, 0, 1, 0)
    private var princessImg: ImageView? = null
    var unit = 1
    var width = 0
    val map = 10

    var handler = Handler();
    fun move() {
        for (i in move) {
            when (i) {
                0 -> go()
                1 -> rotationLeft()
                2 -> rotationRigjt()
            }

        }
    }

    fun setPrincessImage(view: ImageView) {
        this.princessImg = view
    }

    fun setViewSize(width: Int) {
        this.width = width
    }


    fun go() {
        handler.postDelayed({
            run {
                changeXY()
            }
        }, 1000);  // 2000은 2초를 의미합니다.

    }

    fun rotationLeft() {
        unit -= 1
        if (unit < 0)
            unit += 4
    }

    fun rotationRigjt() {
        unit += 1
    }

    fun changeXY() {
        val one = width / map
        unit %= 4
        when (unit) {
            0 -> princessImg!!.y = (princessImg!!.y - one)
            1 -> princessImg!!.x = (princessImg!!.x + one)
            2 -> princessImg!!.y = (princessImg!!.y + one)
            3 -> princessImg!!.x = (princessImg!!.x - one)
        }
        Thread.sleep(100)
    }
}