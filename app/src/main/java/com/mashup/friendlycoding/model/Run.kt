package com.mashup.friendlycoding.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.friendlycoding.repository.CodeBlock

class Run {
    private var moveView = MutableLiveData<Int>()
    private var mCodeBlock: ArrayList<CodeBlock>? = null

    fun setCodeBlock(mCodeBlock: ArrayList<CodeBlock>) {
        this.mCodeBlock = mCodeBlock
    }

    fun getMoving(): LiveData<Int> {
        return moveView
    }

    fun run() {
        for (i in 0 until mCodeBlock!!.size) {
            when (mCodeBlock!![i].funcName) {
                "move();" -> {
                    moveView.value = 0
                    Log.e("갑니다", "0")
                }
                "turnLeft();" -> {
                    moveView.value = 1
                    Log.e("갑니다", "1")
                }
                "turnRight();" -> {
                    moveView.value = 2
                    Log.e("갑니다", "2")
                }
            }
        }
    }
}