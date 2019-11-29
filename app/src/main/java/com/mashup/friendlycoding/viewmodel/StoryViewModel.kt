package com.mashup.friendlycoding.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StoryViewModel : ViewModel(){
    var page = MutableLiveData<Int>()
    var script : Array<String>? = null
    var stageNum = 0

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
            11 -> arrayOf("왕자가 사라졌어!", "왕자를 구출해야 해!", "일단 아버지가 보기 전에 성을 탈출해야 해!", "코드를 터치하면 위에 삽입돼요.", "길게 누르면 삭제해요")
            12 -> arrayOf("성벽을 피해야 해", "왼쪽 오른쪽을 살펴가자", "왕자가 보고 싶어")
            13 -> arrayOf("곧 있으면 성 탈출이야!", "어떻게 가야 도착하는지 예측하자", "가자!")

            // 변수 설명
            21 -> arrayOf("호수에 도착했어", "악마성에서 쓸 마법책이 있네", "줍고 가자!", "위에는 변수라고 해요. 아이템이 있는지 없는지와 그 개수에요")

            // if 설명
            22 -> arrayOf("배가 고파!", "저기 버섯이 있어. 두 개만 먹자!", "하지만 독버섯일 수도 있으니 조심해야 해!", "if는 조건에 다르게 행동하게 해요.\nif를 클릭하면 조건을 넣을 수 있어요.")
            23 -> arrayOf("호수를 건너야 해", "장인에게 나뭇가지를 둘을 주워가자", "부러진 가지인지 잘 보자")

            // for 설명
            31 -> arrayOf("여기다가", "설명을", "적으시면 됩니다.", "for는 반복이에요. 안에 넣은 숫자만큼 반복해요.")




            53-> arrayOf("왕자를 만났어!!!")
            else -> arrayOf("성벽을 피해야 해", "왼쪽 오른쪽을 살펴가자", "왕자가 보고 싶어")
        }
        this.page.value = 0
        this.stageNum = stageNum
    }
}