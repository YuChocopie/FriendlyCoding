package com.mashup.friendlycoding.viewmodel

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModel

class PrincessViewModel : ViewModel() {
    private var princessImg: ImageView? = null
    var xy = true
    var unit = 1
    var width = 0;

    fun setPrincessImage(view: ImageView) {
        this.princessImg = view
    }

    fun setViewSize(width: Int) =
        { -> this.width = width}


    fun go() {
        if (princessImg != null)
            if (xy)
                changeX()
            else
                changeY()
    }

    fun rotation() {
        xy = !xy
        unit = if (!xy) -unit else unit
    }

    fun changeX() {
        princessImg!!.x = (princessImg!!.x + width / 10 * unit)
        Thread.sleep(100)
        Log.e("123123", "" + princessImg!!.x)

    }

    fun changeY() {
        princessImg!!.y = (princessImg!!.y + width / 10 * unit)
        Thread.sleep(100)
        Log.e("123123", "" + princessImg!!.y)

    }
}