package com.mashup.friendlycoding.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StoryViewModel : ViewModel(){
    var page = MutableLiveData<Int>()
    var script : Array<String>? = null

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

    fun init(stageNum : Int) {
        this.script =
        when (stageNum) {
            11 -> arrayOf("왕자가 사라졌어!", "왕자를 구출해야 해!", "일단 아버지가 보기 전에 성을 탈출해야 해!")
            12 -> arrayOf("성벽을 피해야 해", "왼쪽 오른쪽을 살펴가자", "왕자가 보고 싶어")
            else -> null
        }



        this.page.value = 0
    }
}