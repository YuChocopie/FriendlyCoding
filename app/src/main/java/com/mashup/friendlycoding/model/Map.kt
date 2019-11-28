package com.mashup.friendlycoding.model

import android.util.Log

class Map(
    var mapList: Array<Array<Int>>? = null,
    val savedMapList: Array<Array<Int>>? = null,
    var drawables: MapDrawable? = null,
    val startX: Int = 0,
    val startY: Int = 9
) {

    fun clear() {
        for (i in 0..9) {
            System.arraycopy(savedMapList!![i], 0, mapList!![i], 0, 10)
        }
    }

    fun itemPicked(y: Int, x: Int) {
        Log.e("이미지삭제", "($x, $y)")
        this.mapList!![y][x] = 0

    }
}