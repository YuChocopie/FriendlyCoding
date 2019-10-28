package com.mashup.friendlycoding.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.friendlycoding.repository.CodeBlock

class Run {
    var moveView = MutableLiveData<Int>()
    var mCodeBlock = MutableLiveData<ArrayList<CodeBlock>>()

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

    fun clearBlock() {
        moveView.value = -1
        val block = mCodeBlock.value
        mCodeBlock.value!!.clear()
        mCodeBlock.postValue(block)
    }

    fun getMoving(): LiveData<Int> {
        return moveView
    }

    public inner class RunThead : Thread() {
        override fun run() {
            try {
                for (i in 0 until mCodeBlock.value!!.size) {
                    when (mCodeBlock.value!![i].funcName) {
                        "move();" -> {
                            moveView.postValue(0)
                            Log.e("갑니다", "0")
                            sleep(1000)
                        }
                        "turnLeft();" -> {
                            //    moveView.value = 1
                            moveView.postValue(1)
                            Log.e("갑니다", "1")
                            sleep(1000)
                        }
                        "turnRight();" -> {
                            //  moveView.value = 2
                            moveView.postValue(2)
                            Log.e("갑니다", "2")
                            sleep(1000)
                        }
                    }
                }
            } catch (e : IndexOutOfBoundsException) {
                return
            }

        }
    }

    fun run() {
        val run = RunThead()
        run.start()
    }
}