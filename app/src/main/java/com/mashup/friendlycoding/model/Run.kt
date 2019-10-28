package com.mashup.friendlycoding.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.friendlycoding.repository.CodeBlock

class Run {
    private var moveView = MutableLiveData<Int>()
    private var mCodeBlock = MutableLiveData<ArrayList<CodeBlock>>()

    fun getCodeBlock(): LiveData<ArrayList<CodeBlock>> {
        return mCodeBlock
    }

    fun init() {
        mCodeBlock.value = ArrayList<CodeBlock>()
    }

    fun addNewBlock(codeBlock: CodeBlock) {
        Log.e("${codeBlock.funcName} ", "")
        val block = mCodeBlock.value
        mCodeBlock.value!!.add(codeBlock)
        mCodeBlock.postValue(block)
    }

    fun deleteBlock(position : Int) {
        val block = mCodeBlock.value
        mCodeBlock.value!!.removeAt(position)
        mCodeBlock.postValue(block)
    }

    fun getMoving(): LiveData<Int> {
        return moveView
    }

    fun run() {
        for (i in 0 until mCodeBlock.value!!.size) {
            when (mCodeBlock.value!![i].funcName) {
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