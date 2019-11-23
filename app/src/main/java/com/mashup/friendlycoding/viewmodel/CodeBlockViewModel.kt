package com.mashup.friendlycoding.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.model.*

class CodeBlockViewModel : ViewModel() {
    var mDrawables = MapDrawable()
    var mRun = RunModel()

    fun setSettingModel(drawable : MapDrawable) {
        mDrawables = drawable
    }

    fun insertBlockModeOff() {
        mRun.insertBlockAt.postValue(-1)
        Log.e("해제", "${mRun.insertBlockAt.value}")
    }

    fun clearBlock() {
        Log.e("새로고침", " ")
        mRun.clearBlock()
    }

    fun addNewBlock(codeBlock: CodeBlock) {
        Log.e("${codeBlock.funcName} ", "ddddddd")
        mRun.addNewBlock(codeBlock)
    }

    fun deleteBlock(position : Int) {
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

//    @SuppressLint("SetTextI18n")
//    fun insertBlock(view : View?, funcName: String) {
//        if (view == null)
//            return
//        view.findViewById<TextView>(R.id.insertedBlock).text = "$funcName()"
//    }

    fun run() {
        Log.e("RunModel", "실행")
        mRun.run(mDrawables)
    }

    fun runBoss() {
        mRun.metBoss.value = !mRun.metBoss.value!!
    }
}