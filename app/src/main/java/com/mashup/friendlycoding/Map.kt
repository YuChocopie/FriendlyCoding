package com.mashup.friendlycoding

import com.mashup.friendlycoding.model.MapDrawable

class Map (var mapList : Array<Array<Int>>? = null, var drawables: MapDrawable? = null) {
    fun itemPicked(y: Int, x: Int) {
        this.mapList!![y][x] = 0
    }
}