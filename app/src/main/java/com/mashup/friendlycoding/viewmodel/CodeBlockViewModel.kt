package com.mashup.friendlycoding.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.model.RunModel

class CodeBlock(var funcName: String, var argument : Int = 0, var address : Int = 0, var type : Int = 0)

class CodeBlockViewModel : ViewModel() {
    val sunnyImage: Int = R.drawable.ic_sunny

    private var mRun = RunModel()

    fun getRunModel(): RunModel {
        return mRun
    }

    fun clearBlock() {
        mRun.clearBlock()
    }

    fun addNewBlock(codeBlock: CodeBlock) {

        Log.e("${codeBlock.funcName} ", "ddddddd")
        mRun.addNewBlock(codeBlock)
    }

    fun deleteBlock(position: Int) {
        Log.e("삭제합니다", "$position")
        mRun.deleteBlock(position)
    }

    fun coloringNowProcessing(view: View?) {
        if (view == null)
            return
        view.setBackgroundResource(R.color.processing)
    }

    fun coloringNowTerminated(view: View?) {
        if (view == null)
            return
        view.setBackgroundResource(R.color.Invisible)
    }

    fun run() {
        Log.e("RunModel", "실행")
        mRun.run()
    }
}