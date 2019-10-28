package com.mashup.friendlycoding.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.model.Run
import com.mashup.friendlycoding.repository.CodeBlock

class CodeBlockViewModel : ViewModel() {
    val monsterImage: Int = R.drawable.monster
    val princessImage: Int = R.drawable.princess
    val gridImage: Int = R.drawable.grid2

    private var mRun = Run()
    private val blockButton = arrayListOf(
        CodeBlock("move();"),
        CodeBlock("turnLeft();"),
        CodeBlock("turnRight();"),
        CodeBlock("pickAxe();")
    )

    fun getRunModel(): Run {
        return mRun
    }

    fun getBlockButton(): ArrayList<CodeBlock> {
        return blockButton
    }

    fun addNewBlock(codeBlock: CodeBlock) {
        Log.e("${codeBlock.funcName} ", "")
        mRun.addNewBlock(codeBlock)
    }

    fun deleteBlock(position : Int) {
        Log.e("삭제합니다", "$position")
        mRun.deleteBlock(position)
    }

    //TODO : 최종 CodeBlock 들을 실제 실행하는 함수
    fun run(view: View) {
        Log.e("Run", "실행")
        mRun.run()
    }
}