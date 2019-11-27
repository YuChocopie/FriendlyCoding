package com.mashup.friendlycoding.viewmodel

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectStageViewModel : ViewModel() {
    val stageToStart = MutableLiveData<Int>()
    var matrix = ColorMatrix()

    fun init() {
        this.stageToStart.value = -1
    }

    fun goToStage(stage : Int) {
        stageToStart.value = stage
    }

    fun getActTitle (stage : Int) : String {
        return "ACT ${stage/10}"
    }

    fun grayColor(stage:ImageView) {
        matrix.setSaturation(0F)
        var filter = ColorMatrixColorFilter(matrix)
        stage.setColorFilter(filter)
        stage.isEnabled = false
    }

    fun setColor(stage:ImageView){
        matrix.setSaturation(1F)
        var filter = ColorMatrixColorFilter(matrix)
        stage.setColorFilter(filter)
        stage.isEnabled = true
    }
}