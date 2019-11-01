package com.mashup.friendlycoding.model

import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.databinding.ActivityMainBinding
import com.mashup.friendlycoding.viewmodel.CodeBlock


class RunModel {
    private var moveView = MutableLiveData<Int>()
    private var nowProcessing = MutableLiveData<Int>()
    private var nowTerminated = MutableLiveData<Int>()
    private var mCodeBlock = MutableLiveData<ArrayList<CodeBlock>>()
    private var isIterating = false
    private var jumpTo = 0
    private val mPrincess = Princess(10)
    private var index = 0
    private var iterator = 0
    private var blockLevel = 0

    lateinit var mMonster: Monster

    fun getCodeBlock(): LiveData<ArrayList<CodeBlock>> {
        return mCodeBlock
    }

    fun ignoreBlanks(code : String) : String {
        var i = 0
        var start = 0
        while (code[i] == ' ') {
            start++
            i++
        }

        return code.substring(start)
    }

    fun init() {
        mCodeBlock.value = ArrayList()
    }

    fun addNewBlock(codeBlock: CodeBlock) {
//        TODO : 공백 추가하기
//        var tap : String = ""
//        for (i in 0 until blockLevel) {
//            tap += "    "
//        }
//
//        codeBlock.funcName = tap + codeBlock.funcName
//
//        if (ignoreBlanks(codeBlock.funcName) == "for")
//            blockLevel++
//        else if (ignoreBlanks(codeBlock.funcName) == "}") {
//            Log.e("block level", "decreases")
//            blockLevel--
//        }

        Log.e("${codeBlock.funcName} ", "")
        val block = mCodeBlock.value
        mCodeBlock.value!!.add(codeBlock)
        mCodeBlock.postValue(block)
    }
//
//    fun updateBlock(position: Int, cnt: Int) {
//        val block = mCodeBlock.value
//        mCodeBlock.value!![position].count = cnt
//        mCodeBlock.postValue(block)
//    }

    fun deleteBlock(position: Int) {
        val block = mCodeBlock.value
        mCodeBlock.value!!.removeAt(position)
        mCodeBlock.postValue(block)
    }

    fun getMoving(): LiveData<Int> {
        return moveView
    }

    fun getNowProcessing(): LiveData<Int> {
        return nowProcessing
    }

    fun getNowTerminated(): LiveData<Int> {
        return nowTerminated
    }

    fun clearBlock() {
        moveView.value = -1
        val block = mCodeBlock.value
        mCodeBlock.value!!.clear()
        mCodeBlock.postValue(block)
    }

    fun run() {
        val run = RunThead()
        run.start()
        index = 0
        iterator = 0
    }

    inner class RunThead : Thread() {
        lateinit var view: EditText
        override fun run() {
            try {
                moveView.postValue(-2)
                sleep(500)

                while (index < mCodeBlock.value!!.size) {
                    nowProcessing.postValue(index)
                    Log.e("실행 중 : ", ignoreBlanks(mCodeBlock.value!![index].funcName))
                    //updateBlock(i, cnt)
                    //mCodeBlock.value!![i].count
                    //Log.e("test",mCodeBlock.value!![i].funcName)
                    when (ignoreBlanks(mCodeBlock.value!![index].funcName)) {
                        "move" -> {
                            moveView.postValue(0)
                            Log.e("갑니다", "0")
                            sleep(1000)
                        }

                        "turnLeft" -> {
                            //    moveView.value = 1
                            moveView.postValue(1)
                            Log.e("갑니다", "1")
                            sleep(1000)
                        }
                        "turnRight" -> {
                            //  moveView.value = 2
                            moveView.postValue(2)
                            Log.e("갑니다", "2")
                            sleep(1000)
                        }

                        "for" -> {
                            isIterating = true
                            jumpTo = index
                            iterator = mCodeBlock.value!![index].count
                            Log.e("반복", "${mCodeBlock.value!![index].count}")
                            sleep(1000)
                        }

                        "}" -> {
                            if (iterator-- > 1) {
                                index = jumpTo
                                Log.e("한 번 더!", "$iterator")
                            }
                        }
                    }

                    nowTerminated.postValue(index)
                    index++
                }
                moveView.postValue(-3)
            } catch (e: IndexOutOfBoundsException) {
                return
            }
        }
    }
}