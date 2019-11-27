package com.mashup.friendlycoding.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectStageViewModel : ViewModel() {
    val stageToStart = MutableLiveData<Int>()

    fun init() {
        this.stageToStart.value = -1
    }

    fun goToStage(stage : Int) {
        stageToStart.value = stage
    }

    fun getActTitle (stage : Int) : String {
        return "ACT ${stage/10}"
    }
}