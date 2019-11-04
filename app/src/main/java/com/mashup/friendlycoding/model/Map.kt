package com.mashup.friendlycoding.model

class Map (var mapList : Array<Array<Int>>? = null, var drawables: Drawables? = null) {
    fun pickAxe(y : Int, x : Int) {
        this.mapList!![y][x] = 0
    }
}