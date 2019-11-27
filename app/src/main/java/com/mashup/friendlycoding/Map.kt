package com.mashup.friendlycoding

import com.mashup.friendlycoding.model.MapDrawable

class Map (var mapList : Array<Array<Int>>? = null, var drawables: MapDrawable? = null, val startX : Int = 0, val startY : Int = 9) {
    fun itemPicked(y: Int, x: Int) {
        this.mapList!![y][x] = 0
    }
}