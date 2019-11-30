package com.smu.friendlycoding.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TitleViewModel : ViewModel() {
    val goToActivity = MutableLiveData<Int>()

    fun init() {
        goToActivity.value = -1
    }

    fun startGame () {
        goToActivity.value = 0
    }

    fun mute () {
        goToActivity.value = 1
    }

    fun credit() {
        goToActivity.value = 2
    }
}