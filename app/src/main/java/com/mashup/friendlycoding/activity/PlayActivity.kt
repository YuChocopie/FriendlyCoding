package com.mashup.friendlycoding.activity

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.adapter.CodeBlockAdapter
import com.mashup.friendlycoding.adapter.InputCodeBlockAdapter
import com.mashup.friendlycoding.databinding.ActivityPlayBinding
import com.mashup.friendlycoding.model.CodeBlock
import com.mashup.friendlycoding.model.Stage
import com.mashup.friendlycoding.viewmodel.*
import kotlinx.android.synthetic.main.activity_play.*
import java.lang.Thread.sleep

class PlayActivity : BaseActivity() {
    private var mPrincessViewModel = PrincessViewModel()
    private val mCodeBlockViewModel = CodeBlockViewModel()
    private val mMapSettingViewModel = MapSettingViewModel()
    private var mBattleViewModel : BattleViewModel? = null
    private val mRun = mCodeBlockViewModel.mRun
    private lateinit var layoutMainView: View
    private lateinit var stageInfo : Stage
    private var itemNumber: Int = 0
    private var mp: MediaPlayer? = null
    private var stageNum : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityPlayBinding>(this, R.layout.activity_play)
        mp = MediaPlayer.create(this, R.raw.stage2)
        mp!!.isLooping = true

        // 현재 몇 스테이지인지?
        this.stageNum = intent.getIntExtra("stageNum", 0)
        binding.lifecycleOwner = this
        initStage(binding)

        if (this.stageNum == 11) {
            val intent = Intent(this, StoryActivity::class.java)
            startActivity(intent)
        }

        // stageNum 20 넘을 때 visible로 변경
        if (stageNum / 10 > 1) {
            binding.tvCount.isVisible = true
            binding.tvCountSet.isVisible = true
            binding.tvState.isVisible = true
            binding.tvStateSet.isVisible = true
        }

        when (stageNum) {
            21 -> {
                binding.tvCount.setText("bookCnt = ")
                binding.tvState.setText("isBook = ")
            }
            22 -> {
                binding.tvCount.setText("mushroomCnt = ")
            }
            23 -> {
                binding.tvCount.setText("branchCnt = ")
                binding.tvState.setText("Branch = ")
            }
        }

        bossField.isVisible = false

        // 코드 블록의 추가
        mRun.mCodeBlock.observe(this, Observer<List<CodeBlock>> {
            Log.e("추가됨", " ")
            if (mRun.mCodeBlock.value != null) {
                if (mRun.mCodeBlock.value!!.size > 0) {
                    rc_code_block_list.smoothScrollToPosition(mRun.mCodeBlock.value!!.size - 1)
                }
            }
        })

        // 코드 실행 - 객체의 움직임 - View Model 호출
        mRun.moveView.observe(this, Observer<Int> { t ->
            when (t) {
                -2 -> {
                    rc_code_block_list.smoothScrollToPosition(0)
                    Log.e("실행 중", "위로")
                }

                -3 -> {
                    Toast.makeText(this, "클리어 실패", Toast.LENGTH_SHORT).show()
                    Log.e("실행 끝", "위로")
                }

                -4 -> {
                    Toast.makeText(this, "무한 루프", Toast.LENGTH_SHORT).show()
                    Log.e("실행 끝", "위로")
                }

                -5 -> {
                    Log.e("compile", "error")
                    Toast.makeText(this, "컴파일 에러", Toast.LENGTH_SHORT).show()
                }

                -6 -> {
                    Toast.makeText(this, "보스에게 패배하였습니다.", Toast.LENGTH_SHORT).show()
                    boss.text = "보스"
                    constraintLayout.isVisible = true
                    bossField.isVisible = false
                }

                6 -> {  // 아이템 습득
                    itemNumber = resources.getIdentifier("item_" + mRun.changingView.toString(), "id", packageName)
                    Log.e("습득된 아이템", "item_" + mRun.changingView.toString())
                    findViewById<ImageView>(itemNumber).isVisible = false
                    Log.e("좌표를알아보자", "${mRun.changingViewAll}")
                }

                7 -> {  // 패배
                    mCodeBlockViewModel.clearBlock()
                    mPrincessViewModel.clear()
                }

                8 -> {  // 승리
                    binding.tvWin.isVisible = true
                }

                9 -> {  // 종료
                    finish()
                }

                1 -> {
                    mPrincessViewModel.move(true)
                }

                2->{
                    mPrincessViewModel.move(false)
                }
            }
        })

        // 코드 실행 - 현재 실행 중인 블록의 배경 색칠하기
        mRun.nowProcessing.observe(this, Observer<Int> { t ->
            Log.e("현재 실행 위치", "$t")
            if (t > 8)
                rc_code_block_list.smoothScrollToPosition(t + 3)
            mCodeBlockViewModel.coloringNowProcessing(rc_code_block_list.findViewHolderForAdapterPosition(t))
        })

        // 코드 실행 - 현재 실행이 끝난 블록의 배경 끄기
        mRun.nowTerminated.observe(this, Observer<Int> { t ->
            mCodeBlockViewModel.coloringNowTerminated(rc_code_block_list.findViewHolderForAdapterPosition(t))
        })

        mRun.princessAction.observe(this, Observer<Int> { t ->
            mBattleViewModel?.princessActionOn(t)
        })

        mRun.monsterAttack.observe(this, Observer<Int> { t ->
            mBattleViewModel?.bossAttackOn(t)
        })

        // 공주가 보스를 만남
        mRun.metBoss.observe(this, Observer<Boolean> { t ->
            boss.text = if (t) "OFF" else "보스"
            constraintLayout.isVisible = !t
            bossField.isVisible = t

            if (t) {
                val block = mRun.mCodeBlock.value
                block!!.clear()
                mRun.mCodeBlock.postValue(block)
                mCodeBlockViewModel.adapter.notifyDataSetChanged()

                mBattleViewModel = BattleViewModel(binding.hpBar, binding.princess, binding.monster, binding.princessAttackMotion)
                mBattleViewModel!!.mRun = mRun
                mBattleViewModel!!.princessAction = stageInfo.princessAction
                mBattleViewModel!!.init()
                mBattleViewModel!!.playActivity = this

                mRun.mMonster = stageInfo.monster
                binding.battleVM = mBattleViewModel
                Toast.makeText(this, "보스를 만났어요", Toast.LENGTH_SHORT).show()

                mCodeBlockViewModel.setOfferedBlock(mMapSettingViewModel.bossBattleBlock!!)
            }
            else {
                mBattleViewModel = null
                binding.battleVM = null
                mCodeBlockViewModel.setOfferedBlock(mMapSettingViewModel.offeredBlock)

                if (mRun.bossKilled) {
                    Toast.makeText(this, "보스를 물리쳤어요", Toast.LENGTH_SHORT).show()
                    val blockclear = mRun.mCodeBlock.value
                    blockclear!!.clear()
                    mRun.mCodeBlock.postValue(blockclear)
                    mCodeBlockViewModel.adapter.notifyDataSetChanged()

                    val block = mRun.backup
                    mRun.mCodeBlock.postValue(block)
                    mCodeBlockViewModel.adapter.notifyDataSetChanged()
                    mCodeBlockViewModel.run()
                }
            }
        })

        mRun.monsterAttacked.observe(this, Observer<Boolean> { t ->
            if (t) {
                Log.e("공주의 공격!", "현재 HP : ${mRun.mMonster!!.getHP()}")
                mBattleViewModel!!.monsterAttacked()
            } else {
                princess_attack_motion.isVisible = false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mp!!.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mp!!.stop()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        Log.e("Layout Width - ", "Width" + (layoutMainView.width))
        mPrincessViewModel.setViewSize(layoutMainView.width)
        mMapSettingViewModel.setViewSize(layoutMainView.width)
    }

    fun initStage(binding : ActivityPlayBinding) {
        this.layoutMainView = this.findViewById(R.id.constraintLayout)

        // Princess View Model과 bind
        binding.princessVM = this.mPrincessViewModel

        // Code Block View Model과 bind
        binding.codeBlockVM = this.mCodeBlockViewModel
        this.mCodeBlockViewModel.init()
        this.mRun.init()

        binding.mapSettingVM = this.mMapSettingViewModel

        this.stageInfo = this.mMapSettingViewModel.mMapSettingModel.getStageInfo(this.stageNum)
        val drawableInfo = this.stageInfo.map.drawables!!
        this.mMapSettingViewModel.setStage(this.stageInfo, this)
        this.mPrincessViewModel.setPrincessImage(drawableInfo, this)
        this.mCodeBlockViewModel.setSettingModel(drawableInfo)
        this.mCodeBlockViewModel.setOfferedBlock(this.mMapSettingViewModel.offeredBlock)

        this.mRun.mMap = this.stageInfo.map
        this.mRun.mPrincess = this.stageInfo.princess
        this.mRun.mClearCondition = this.stageInfo.clearCondition
        this.mRun.mPrincessViewModel = this.mPrincessViewModel
        this.mRun.mCodeBlockViewModel = this.mCodeBlockViewModel
    }
}