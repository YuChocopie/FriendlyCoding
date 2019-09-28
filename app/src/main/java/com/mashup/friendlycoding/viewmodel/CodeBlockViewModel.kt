package com.mashup.friendlycoding.viewmodel

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.friendlycoding.PlayActivity
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.repository.CodeBlock
import com.mashup.friendlycoding.repository.CodeBlocksRepository

class CodeBlockViewModel : ViewModel() {
    val monsterImage: Int = R.drawable.monster
    val princessImage: Int = R.drawable.princess
    val gridImage: Int = R.drawable.grid

    private var mCodeBlock: MutableLiveData<ArrayList<CodeBlock>>? = null
    private var mRepo = CodeBlocksRepository()
    private var mIsUpdating = MutableLiveData<Boolean>(false)
    private val blockButton = arrayListOf(CodeBlock("move();"),
        CodeBlock("turnLeft();"),
        CodeBlock("turnRight();"),
        CodeBlock("pickAxe();"))

    fun getBlockButton () : ArrayList<CodeBlock> {
        return blockButton
    }

    fun init() {
        if (mCodeBlock != null) {
            return
        }
        mRepo = CodeBlocksRepository().getInstance()
        mCodeBlock = mRepo.getCodeBlock()
    }

    fun getCodeBlock () : LiveData<ArrayList<CodeBlock>>? {
        return mCodeBlock
    }

    fun addNewBlock(codeBlock : CodeBlock) {
        mIsUpdating.value = true
        Log.i("${codeBlock.funcName} ", "")
        val currentPlaces : ArrayList<CodeBlock> = mCodeBlock!!.value!!
        mCodeBlock!!.value!!.add(codeBlock)
        mCodeBlock!!.postValue(currentPlaces)
        mIsUpdating.postValue(false)
    }

    //TODO : 최종 CodeBlock 들을 실제 실행하는 함수
    fun run(view : View) {
        Log.e("PlayActivity", "실행")
        val context = view.context
        val intent = Intent(context, PlayActivity::class.java)
        context.startActivity(intent)
    }
}