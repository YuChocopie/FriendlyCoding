package com.mashup.friendlycoding.model

import android.util.Log

class Map (var mapList : Array<Array<Int>>? = null, var drawables: MapDrawable? = null, val startX : Int = 0, val startY : Int = 9,var stageNum:Int=10) {
    fun itemPicked(y: Int, x: Int) {
        Log.e("이미지삭제","($x, $y)")
//        this.mapList!![y][x] = 0
    }
}