package com.mashup.friendlycoding.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StoryViewModel : ViewModel(){
    var page = MutableLiveData<Int>()
    val script = arrayOf("왕자가 사라졌어!", "왕자를 구출해야 해!", "일단 아버지가 보기 전에 성을 탈출해야 해!")

    fun prevPage() {
        Log.e("페이지", "${this.page.value!!}")
        if (this.page.value!! > 0) {
            this.page.postValue(this.page.value!! - 1)
        }
    }

    fun nextPage() {
        Log.e("페이지", "${this.page.value!!}")
        this.page.postValue(this.page.value!! + 1)
    }

    fun init() {
        this.page.value = 0
    }
}