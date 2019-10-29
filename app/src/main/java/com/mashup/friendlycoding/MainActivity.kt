package com.mashup.friendlycoding

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.friendlycoding.adapter.CodeBlockAdapter
import com.mashup.friendlycoding.adapter.InputCodeBlockAdapter
import com.mashup.friendlycoding.databinding.ActivityMainBinding
import com.mashup.friendlycoding.repository.CodeBlock
import com.mashup.friendlycoding.viewmodel.CodeBlockViewModel
import com.mashup.friendlycoding.viewmodel.PrincessViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Build
import android.graphics.Color.WHITE
import androidx.recyclerview.widget.RecyclerView
import android.view.MotionEvent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class MainActivity : BaseActivity() {
    private var mPrincessViewModel = PrincessViewModel()
    lateinit var layoutMainView: View
    private val mCodeBlockViewModel = CodeBlockViewModel()
    private val mRun = mCodeBlockViewModel.getRunModel()

    //수평으로 바꾸어주는 매니저
    val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    lateinit var mAdapter: CodeBlockAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        layoutMainView = this.findViewById(R.id.constraintLayout)

        // bind Princess View Model
        binding.princessVM = mPrincessViewModel
        mPrincessViewModel.setPrincessImage(binding.ivPrinsecess,binding.tvWin)

        // bind Code Block View Model
        binding.codeBlockVM = mCodeBlockViewModel
        binding.codeBlock = mCodeBlockViewModel.getBlockButton()
        mRun.init()

        //recycler view connects
        mAdapter = CodeBlockAdapter(this, mRun.getCodeBlock().value!!)
        val linearLayoutManager = LinearLayoutManager(this)
        rc_code_block_list.layoutManager = linearLayoutManager
        rc_code_block_list.adapter = mAdapter

        //input code adapter
        val mInputdapter = InputCodeBlockAdapter(mCodeBlockViewModel, mAdapter)
        rc_input_code.adapter = mInputdapter
        rc_input_code.layoutManager = layoutManager

        //경계선
        rc_input_code.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        )

        mRun.getCodeBlock().observe(this, Observer<List<CodeBlock>> {
            Log.e("추가됨", " ")
            mAdapter.notifyDataSetChanged()
            if (mRun.getCodeBlock().value!!.size > 1) {
                rc_code_block_list.smoothScrollToPosition(mRun.getCodeBlock().value!!.size - 1)
            }
        })

        mRun.getMoving().observe(this, Observer<Int> { t ->
            if (t == -2) {
                rc_code_block_list.smoothScrollToPosition(0)
                Log.e("실행 중", "위로")
                mAdapter.clickable = false
                mInputdapter.clickable = false
            }

            else if (t == -3) {
                Log.e("실행 끝", "위로")
                mAdapter.clickable = true
                mInputdapter.clickable = true
            }
            else
                mPrincessViewModel.move(t)
        })

        mRun.getNowProcessing().observe(this, Observer<Int> { t ->
            mCodeBlockViewModel.coloringNowProcessing(linearLayoutManager.findViewByPosition(t))
            if (t > 8)
                rc_code_block_list.smoothScrollToPosition(mAdapter.itemCount - 1)
        })

        mRun.getNowTerminated().observe(this, Observer<Int> { t ->
            mCodeBlockViewModel.coloringNowTerminated(linearLayoutManager.findViewByPosition(t))
        })

        mPrincessViewModel.isLost.observe(this, Observer<Boolean> {t ->
            if (t) {
                mCodeBlockViewModel.clearBlock()
                mPrincessViewModel.clear()
                mPrincessViewModel.isLost.value = false
            }
        })
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        Log.e("Layout Width - ", "Width" + (layoutMainView.width))
        mPrincessViewModel.setViewSize(layoutMainView.width)
    }

    fun disableRecyclerView (rcView : RecyclerView, enable : Boolean) {
        rcView.addOnItemTouchListener(object :
            RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                // true: consume touch event
                // false: dispatch touch event
                return !enable
            }
        })
    }
}