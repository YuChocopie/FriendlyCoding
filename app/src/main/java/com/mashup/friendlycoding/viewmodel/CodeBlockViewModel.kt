package com.mashup.friendlycoding.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.model.RunModel

class CodeBlock(var funcName: String, var argument : Int = 0, var address : Int = 0)

class CodeBlockViewModel : ViewModel() {
    val monsterImage: Int = R.drawable.monster
    val sunnyImage: Int = R.drawable.ic_sunny
    val princessImage: Int = R.drawable.princess
    val gridImage: Int = R.drawable.grid2
    //var count: Int = 1//count 받아오기

    private var mRun = RunModel()
    private val blockButton = arrayListOf(
        CodeBlock("move"),
        CodeBlock("turnLeft"),
        CodeBlock("turnRight"),
        CodeBlock("pickAxe"),
        CodeBlock("for"),
        CodeBlock("}")
    )

    fun getRunModel(): RunModel {
        return mRun
    }

    fun getBlockButton(): ArrayList<CodeBlock> {
        return blockButton
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