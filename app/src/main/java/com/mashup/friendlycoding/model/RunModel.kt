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
import com.mashup.friendlycoding.ignoreBlanks
import com.mashup.friendlycoding.viewmodel.CodeBlock
import java.util.*
import kotlin.collections.ArrayList

class RunModel {
    private var moveView = MutableLiveData<Int>()
    private var nowProcessing = MutableLiveData<Int>()
    private var nowTerminated = MutableLiveData<Int>()
    private var mCodeBlock = MutableLiveData<ArrayList<CodeBlock>>()
    private var jumpTo = 0
    private val mPrincess = Princess(10)
    private var IR = 0  // 명령어 실행할 주소
    private var iterator = 0 // 반복자
    private var blockLevel = 0 // 들여쓰기 정도.
    private var bracketStack = Stack<Int>()  // 괄호 체크, 그와 동시에 jump 할 명령어 주소 얻기 위함
    lateinit var mMonster: Monster

    fun getCodeBlock(): LiveData<ArrayList<CodeBlock>> {
        return mCodeBlock
    }

    fun init() {
        mCodeBlock.value = ArrayList()
    }

    fun addNewBlock(codeBlock: CodeBlock) {
//        TODO : 공백 추가하기
        val adding = CodeBlock(codeBlock.funcName, address = IR)

        if (adding.funcName == "}") {
            adding.address = bracketStack.peek()  // jump할 주소
            bracketStack.pop()
            Log.e("block level", "decreases")
            blockLevel--
        }

        var tap = ""
        for (i in 0 until blockLevel) {
            tap += "    "
        }

        Log.e("${adding.funcName} ", "xcxcxcxcx")
        adding.funcName = tap + adding.funcName

        if (ignoreBlanks(adding.funcName) == "for") {
            bracketStack.push(IR)
            blockLevel++
            Log.e("블록하강 ", "qqqqqqq")
        }

        IR++
        val block = mCodeBlock.value
        block!!.add(adding)
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
        IR--
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
        iterator = 0
        jumpTo = 0
        blockLevel = 0
        moveView.postValue(-1)
        val block = mCodeBlock.value
        mCodeBlock.value!!.clear()
        mCodeBlock.postValue(block)
    }

    fun run() {
        if (!bracketStack.empty()) {
            Log.e("compile", "error")
            clearBlock()
            return
        }

        val run = RunThead()
        IR = 0
        run.start()
        //clearBlock()
        iterator = 0
    }

    inner class RunThead : Thread() {
        lateinit var view: EditText
        override fun run() {
            try {
                moveView.postValue(-2)
                sleep(500)

                while (IR < mCodeBlock.value!!.size) {
                    nowProcessing.postValue(IR)
                    Log.e("실행 중 : ", mCodeBlock.value!![IR].funcName)
                    //updateBlock(i, cnt)
                    //mCodeBlock.value!![i].count
                    //Log.e("test",mCodeBlock.value!![i].funcName)
                    when (ignoreBlanks(mCodeBlock.value!![IR].funcName)) {
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
                            //iteratorStack.push(mCodeBlock.value!![IR].argument)
                            iterator = mCodeBlock.value!![IR].argument
                            Log.e("반복", "${mCodeBlock.value!![IR].argument}")
                            sleep(1000)
                        }

                        "}" -> {
                            jumpTo = mCodeBlock.value!![IR].address
                            if (mCodeBlock.value!![jumpTo].argument-- > 1) {
                                IR = jumpTo
                                Log.e(
                                    "한 번 더!",
                                    "${mCodeBlock.value!![jumpTo].argument}   ${mCodeBlock.value!![IR].funcName}   "
                                )
                            } else {
                                mCodeBlock.value!![jumpTo].argument = iterator
                            }
//
//                            else {
//                                if (!iteratorStack.empty()) {
//                                    iteratorStack.pop()
//
//                                    if (!iteratorStack.empty()) {
//                                        iterator = iteratorStack.peek()
//                                    }
//                                    else
//                                        iterator = 0
//                                }
//                            }
                            sleep(1000)
                        }
                    }

                    nowTerminated.postValue(IR)
                    IR++  // PC
                }
                moveView.postValue(-3)
            } catch (e: IndexOutOfBoundsException) {
                return
            }
            //clearBlock() 클리어 실패 시
        }
    }
}