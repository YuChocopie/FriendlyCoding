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

    var handler = Handler()
    fun move(i: Int) {
        when (i) {
            0 -> go()
            1 -> rotationLeft()
            2 -> rotationRight()
        }
    }

    fun setPrincessImage(view: ImageView) {
        this.princessImg = view
    }

    fun setViewSize(width: Int) {
        this.width = width
    }

    fun go() {
//        handler.postDelayed({
//            run {
//                changeXY()
//            }
//        }, 2000) // 2000은 2초를 의미합니다.
        changeXY()
        Thread.sleep(200)
    }

    fun rotationLeft() {
        unit -= 1
        if (unit < 0)
            unit += 4
    }

    fun rotationRight() {
        //Thread.sleep(1000)
        //unit += 1
        if (unit == 3)
            unit = 0
        else
            unit++
    }

    fun changeXY() {
        val one = width / map
        unit %= 4
        when (unit) {
            //goint up
            0 -> princessImg!!.y = (princessImg!!.y - one)

            //going right
            1 -> princessImg!!.x = (princessImg!!.x + one)

            //going down
            2 -> princessImg!!.y = (princessImg!!.y + one)

            //going left
            3 -> princessImg!!.x = (princessImg!!.x - one)
        }

        //Thread.sleep(100)
    }
}