package com.mashup.friendlycoding.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.adapter.CodeBlockAdapter
import com.mashup.friendlycoding.adapter.InputCodeBlockAdapter
import com.mashup.friendlycoding.model.*

class CodeBlockViewModel : ViewModel() {
    var mDrawables = MapDrawable()
    var mRun = RunModel()
    lateinit var adapter : CodeBlockAdapter
    lateinit var inputAdapter : InputCodeBlockAdapter
    var isRunning = MutableLiveData<Boolean>()

    fun setSettingModel(drawable : MapDrawable) {
        mDrawables = drawable
    }

    fun init() {
        this.adapter = CodeBlockAdapter(R.layout.item_code_block, this)
        this.adapter.notifyDataSetChanged()
        this.inputAdapter = InputCodeBlockAdapter(R.layout.item_input_code, this)
        this.inputAdapter.notifyDataSetChanged()
        isRunning.value = false
    }

    fun setOfferedBlock (offeredBlock: ArrayList<CodeBlock>) {
        this.inputAdapter.setAddingBlock(offeredBlock)
        this.inputAdapter.notifyDataSetChanged()
    }

    fun insertBlockModeOff() {
        mRun.insertBlockAt.postValue(-1)
        Log.e("해제", "${mRun.insertBlockAt.value}")
    }

    fun clearBlock() {
        Log.e("새로고침", " ")
        mRun.clearBlock()
        this.adapter.notifyDataSetChanged()
    }

    fun addNewBlock(codeBlock: CodeBlock) {
        if (this.isRunning.value!!)
            return

        Log.e("${codeBlock.funcName} ", "ddddddd")
        mRun.addNewBlock(codeBlock)
        this.adapter.notifyDataSetChanged()
    }

    fun deleteBlock(position : Int) {
        if (this.isRunning.value!!)
            return
        mRun.deleteBlock(position)
        this.adapter.notifyItemRemoved(position)
        this.adapter.notifyItemRangeChanged(position, mRun.mCodeBlock.value!!.size)
    }

    fun coloringNowProcessing(view : RecyclerView.ViewHolder?) {
        if (view == null)
            return
        view.itemView.setBackgroundResource(R.color.processing)
    }

    fun coloringNowTerminated(view: RecyclerView.ViewHolder?) {
        if (view == null)
            return
        view.itemView.setBackgroundResource(R.color.Invisible)
    }

    fun run() {
        Log.e("RunModel", "실행")
        mRun.run(mDrawables)
    }

    fun runBoss() {
        mRun.metBoss.value = !mRun.metBoss.value!!
    }

    fun getCodeBlock(position: Int) : CodeBlock {
        return mRun.mCodeBlock.value!![position]
    }

    fun getCodeBlockAdapter() : CodeBlockAdapter {
        return this.adapter
    }

    fun getInputCodeBlockAdapter() : InputCodeBlockAdapter {
        return this.inputAdapter
    }

    fun getEndText(position : Int) : String {
        val type = mRun.mCodeBlock.value!![position].type

        return if (type == 2 || type == 4)
            "{"
        else if (type == 1)
            ") {"
        else {
            ""
        }
    }

    fun visibleControl (position : Int) : Int {
        val type = mRun.mCodeBlock.value!![position].type

        return if (type == 1) {
            View.VISIBLE
        } else
            View.GONE
    }

    fun insertBlock(position: Int) {
        if (this.isRunning.value!!)
            return

        val type = mRun.mCodeBlock.value!![position].type
        if (type == 2 || type == 4)
            mRun.insertBlockPosition = position
        else
            -1
    }
}