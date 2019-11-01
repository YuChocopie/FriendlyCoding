package com.mashup.friendlycoding.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.adapter.CodeBlockAdapter
import com.mashup.friendlycoding.adapter.InputCodeBlockAdapter
import com.mashup.friendlycoding.databinding.ActivityMainBinding
import com.mashup.friendlycoding.viewmodel.BattleViewModel
import com.mashup.friendlycoding.viewmodel.CodeBlock
import com.mashup.friendlycoding.viewmodel.CodeBlockViewModel
import com.mashup.friendlycoding.viewmodel.PrincessViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private var mPrincessViewModel = PrincessViewModel()
    lateinit var layoutMainView: View
    private val mCodeBlockViewModel = CodeBlockViewModel()
    private val mRun = mCodeBlockViewModel.getRunModel()
    private var mBattleViewModel : BattleViewModel? = null

    //수평으로 바꾸어주는 매니저
    val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    lateinit var mAdapter: CodeBlockAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        )
        binding.lifecycleOwner = this

        layoutMainView = this.findViewById(R.id.constraintLayout)

        // bind Princess View Model
        binding.princessVM = mPrincessViewModel
        mPrincessViewModel.setPrincessImage(binding.ivPrinsecess, binding.tvWin)

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
//        rc_input_code.addItemDecoration(
//            DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
//        )

        bossField.isVisible = false

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
            } else if (t == -3) {
                Log.e("실행 끝", "위로")
                mAdapter.clickable = true
                mInputdapter.clickable = true
            } else
                mPrincessViewModel.move(t)
        })

        mRun.getNowProcessing().observe(this, Observer<Int> { t ->
            mCodeBlockViewModel.coloringNowProcessing(linearLayoutManager.findViewByPosition(t))
            if (t > 8)
                rc_code_block_list.smoothScrollToPosition(t + 3)
        })

        mRun.getNowTerminated().observe(this, Observer<Int> { t ->
            mCodeBlockViewModel.coloringNowTerminated(linearLayoutManager.findViewByPosition(t))
        })

        mPrincessViewModel.isLost.observe(this, Observer<Boolean> { t ->
            if (t) {
                mCodeBlockViewModel.clearBlock()
                mPrincessViewModel.clear()
                mPrincessViewModel.isLost.value = false
            }
        })

        mPrincessViewModel.metBoss.observe(this, Observer<Boolean> { t ->
            // 보스를 만났거나 만나지 않았을 때 뷰의 전환
            // 보스의 의미 : 함수의 호출이므로 사실 실제 앱에서는 clearBlock과 clear를 하면 안 된다.
            // 함수가 정상적으로 처리되면 (보스를 이기면) 원래의 맵으로 돌아오고, 그 다음 코드를 실행해야 한다.
            boss.text = if (t) "OFF" else "보스"
            constraintLayout.isVisible = !t
            bossField.isVisible = t
            mCodeBlockViewModel.clearBlock()
            mPrincessViewModel.clear()
            mAdapter.clickable = true
            mInputdapter.clickable = true

            if (t) {
                mBattleViewModel = BattleViewModel()
                binding.battleVM = mBattleViewModel
                Toast.makeText(this, "보스를 만났어요", Toast.LENGTH_SHORT).show()
            }

            else {
                mBattleViewModel = null
                binding.battleVM = null
                Toast.makeText(this, "보스를 물리쳤어요", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        Log.e("Layout Width - ", "Width" + (layoutMainView.width))
        mPrincessViewModel.setViewSize(layoutMainView.width)
    }
}