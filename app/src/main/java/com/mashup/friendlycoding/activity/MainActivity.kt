package com.mashup.friendlycoding.activity

import android.content.Intent
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
import com.mashup.friendlycoding.viewmodel.*
import kotlinx.android.synthetic.main.activity_main.*
import com.mashup.friendlycoding.model.Map

class MainActivity : BaseActivity() {
    private var mPrincessViewModel = PrincessViewModel()
    private val mCodeBlockViewModel = CodeBlockViewModel()
    private val mMapSettingViewModel = MapSettingViewModel()
    private var mBattleViewModel : BattleViewModel? = null
    private val mRun = mCodeBlockViewModel.getRunModel()
    private lateinit var layoutMainView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        )
        binding.lifecycleOwner = this

        // 현재 몇 스테이지인지?
        val stageNum = intent.getIntExtra("stageNum", 0)

        layoutMainView = this.findViewById(R.id.constraintLayout)

        // Princess View Model과 bind
        binding.princessVM = mPrincessViewModel
        mPrincessViewModel.setPrincessImage(binding.ivPrinsecess, binding.tvWin)

        // Code Block View Model과 bind
        binding.codeBlockVM = mCodeBlockViewModel
        mRun.init()

        // Map Setting View Model과 bind 후 stageInfo 얻어오기
        binding.mapSettingVM = mMapSettingViewModel
        val stageInfo = mMapSettingViewModel.mMapSettingModel.getStageInfo(stageNum)

        mMapSettingViewModel.mDrawables = stageInfo.map.drawables!!
        mMapSettingViewModel.offeredBlock = stageInfo.block
        mRun.mMap = stageInfo.map
        mRun.mPrincess = stageInfo.princess
        mRun.mMonster = stageInfo.monster

//        for (i in 0 until stageInfo.map.drawables!!.itemImg.size) {
//
//        }

        // 코드 블록의 리사이클러 뷰 연결
        val mAdapter = CodeBlockAdapter(this, mRun.getCodeBlock().value!!)
        val linearLayoutManager = LinearLayoutManager(this)
        rc_code_block_list.layoutManager = linearLayoutManager
        rc_code_block_list.adapter = mAdapter

        // 입력될 블록의 리사이클러 뷰 연결
        val mInputdapter = InputCodeBlockAdapter(mCodeBlockViewModel, mMapSettingViewModel)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rc_input_code.adapter = mInputdapter
        rc_input_code.layoutManager = layoutManager

        //경계선
//        rc_input_code.addItemDecoration(
//            DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
//        )

        bossField.isVisible = false

        // 코드 블록의 추가
        mRun.getCodeBlock().observe(this, Observer<List<CodeBlock>> {
            Log.e("추가됨", " ")
            mAdapter.notifyDataSetChanged()
            if (mRun.getCodeBlock().value!!.size > 1) {
                rc_code_block_list.smoothScrollToPosition(mRun.getCodeBlock().value!!.size - 1)
            }
        })

        // 코드 실행 - 객체의 움직임 - View Model 호출
        mRun.getMoving().observe(this, Observer<Int> { t ->
            when (t) {
                -2 -> {
                    rc_code_block_list.smoothScrollToPosition(0)
                    Log.e("실행 중", "위로")
                    mAdapter.clickable = false
                    mInputdapter.clickable = false
                }
                -3 -> {
                    Log.e("실행 끝", "위로")
                    mAdapter.clickable = true
                    mInputdapter.clickable = true
                }
                else -> mPrincessViewModel.move(t)
            }
        })

        // 코드 실행 - 현재 실행 중인 블록의 배경 색칠하기
        mRun.getNowProcessing().observe(this, Observer<Int> { t ->
            mCodeBlockViewModel.coloringNowProcessing(linearLayoutManager.findViewByPosition(t))
            if (t > 8)
                rc_code_block_list.smoothScrollToPosition(t + 3)
        })

        // 코드 실행 - 현재 실행이 끝난 블록의 배경 끄기
        mRun.getNowTerminated().observe(this, Observer<Int> { t ->
            mCodeBlockViewModel.coloringNowTerminated(linearLayoutManager.findViewByPosition(t))
        })

        // 공주가 패배할 시
        mRun.isLost.observe(this, Observer<Boolean> { t ->
            if (t) {
                mCodeBlockViewModel.clearBlock()
                mPrincessViewModel.clear()
                mRun.isLost.value = false
            }
        })

        // 공주가 이길 시
        mRun.isWin.observe(this, Observer<Boolean> { t ->
            if (t) {
                binding.tvWin.isVisible = true
            }
        })

        // 공주가 보스를 만남
        mRun.metBoss.observe(this, Observer<Boolean> { t ->
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

        // 보스전 테스트를 위한 임시 코드
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