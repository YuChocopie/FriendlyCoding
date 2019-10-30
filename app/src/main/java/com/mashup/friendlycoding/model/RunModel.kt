package com.mashup.friendlycoding.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.friendlycoding.viewmodel.CodeBlock

class RunModel {
    private var moveView = MutableLiveData<Int>()
    private var nowProcessing = MutableLiveData<Int>()
    private var nowTerminated = MutableLiveData<Int>()
    private var mCodeBlock = MutableLiveData<ArrayList<CodeBlock>>()
    private val mPrincess = Princess(10)

    lateinit var mMonster: Monster

    fun getCodeBlock(): LiveData<ArrayList<CodeBlock>> {
        return mCodeBlock
    }

    fun init() {
        mCodeBlock.value = ArrayList()

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

    fun getNowProcessing() : LiveData<Int> {
        return nowProcessing
    }

    fun getNowTerminated() : LiveData<Int> {
        return nowTerminated
    }

    fun clearBlock() {
        moveView.value = -1
        val block = mCodeBlock.value
        mCodeBlock.value!!.clear()
        mCodeBlock.postValue(block)
    }

    inner class RunThead : Thread() {
        override fun run() {
            try {
                moveView.postValue(-2)
                sleep(500)

                for (i in 0 until mCodeBlock.value!!.size) {
                    nowProcessing.postValue(i)
                    Log.e("실행 중 : ", "$i")

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

                    nowTerminated.postValue(i)
                }
                moveView.postValue(-3)
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