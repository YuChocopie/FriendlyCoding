package com.mashup.friendlycoding.activity

import android.app.Activity
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
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.databinding.ActivityPlayBinding
import com.mashup.friendlycoding.model.*
import com.mashup.friendlycoding.viewmodel.BattleViewModel
import com.mashup.friendlycoding.viewmodel.CodeBlockViewModel
import com.mashup.friendlycoding.viewmodel.MapSettingViewModel
import com.mashup.friendlycoding.viewmodel.PrincessViewModel
import kotlinx.android.synthetic.main.activity_play.*


class PlayActivity : BaseActivity() {
    private var mPrincessViewModel = PrincessViewModel()
    private val mCodeBlockViewModel = CodeBlockViewModel()
    private val mMapSettingViewModel = MapSettingViewModel()
    private var mBattleViewModel: BattleViewModel? = null
    private val mRun = mCodeBlockViewModel.mRun
    private lateinit var layoutMainView: View
    private lateinit var stageInfo: Stage
    private var itemNumber: Int = 0
    private var mp: MediaPlayer? = null
    private var stageNum: Int = 0
    lateinit var binding: ActivityPlayBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = DataBindingUtil.setContentView<ActivityPlayBinding>(this, R.layout.activity_play)

        // 현재 몇 스테이지인지?
        this.stageNum = intent.getIntExtra("stageNum", 0)
        binding.lifecycleOwner = this
        initStage(binding)

//        btnClear.setOnClickListener {
//            restart()
//        }

        val intent = Intent(this, StoryActivity::class.java)
        intent.putExtra("stageNum", this.stageNum)
        startActivity(intent)

        Log.e("stageNum", "$stageNum")

        // stageNum 20 넘을 때 visible로 변경
        if (stageNum / 10 > 1) {
            binding.tvCount.isVisible = true
            binding.tvCountSet.isVisible = true
            binding.tvState.isVisible = true
            binding.tvStateSet.isVisible = true
        }

        when (stageNum) {
            21 -> {
                binding.tvCount.text = "bookCnt = "
                binding.tvState.text = "isBook = "
            }
            22 -> {
                binding.tvState.setText("isMushroom = ")
                binding.tvCount.setText("mushroomCnt = ")
            }
            23 -> {
                binding.tvCount.setText("branchCnt = ")
                binding.tvState.setText("isBranch = ")
            }
            31 -> {
                binding.tvCount.text = "rockCnt = "
                binding.tvState.text = "isRock = "
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
                START_RUN -> {
                    rc_code_block_list.smoothScrollToPosition(0)
                    Log.e("실행 중", "위로")
                }

                END_RUN -> {
                    Toast.makeText(this, "클리어 실패", Toast.LENGTH_SHORT).show()
                    Log.e("실행 끝", "위로")
                }

                INFINITE_LOOP -> {
                    Toast.makeText(this, "무한 루프", Toast.LENGTH_SHORT).show()
                    Log.e("실행 끝", "위로")
                }

                COMPILE_ERROR -> {
                    Log.e("compile", "error")
                    Toast.makeText(this, "컴파일 에러", Toast.LENGTH_SHORT).show()
                }

                LOST_BOSS_BATTLE -> {
                    Toast.makeText(this, "보스에게 패배하였습니다.", Toast.LENGTH_SHORT).show()
                }

                ITEM_PICKED -> {  // 아이템 습득
                    itemNumber = resources.getIdentifier(
                        "item_" + mRun.changingView.toString(),
                        "id",
                        packageName
                    )
                    Log.e("습득된 아이템", "item_" + mRun.changingView.toString())

                    if (itemNumber != 0) {
                        findViewById<ImageView>(itemNumber).isVisible = false
                    }

                    for (i in 0 until mMapSettingViewModel.mDrawables.item.size) {
                        Log.e("i를알아보자", "$i")
                        if (mMapSettingViewModel.mDrawables.item[i].item_id == PICKAXE) {
                            mPrincessViewModel.axeImg!!.isVisible = true
                        }
                    }
                }

                CRUSH_ROCK -> {  // 아이템 습득
                    itemNumber = resources.getIdentifier(
                        "item_" + mRun.changingView.toString(),
                        "id",
                        packageName
                    )
                    Log.e("습득된 아이템", "item_" + mRun.changingView.toString())
                    if (itemNumber != 0) {
                        findViewById<ImageView>(itemNumber).isVisible = false
                    }
                }

                PLAYER_LOST -> {  // 패배
                    //restart()
                    Toast.makeText(this, "클리어 실패", Toast.LENGTH_SHORT).show()
                    constraintLayout.isVisible = true
                    bossField.isVisible = false
                    val itemSize = mMapSettingViewModel.itemSize()
                    for (i in 0 until itemSize) {
                        findViewById<ImageView>(
                            resources.getIdentifier(
                                "item_" + (i + 1).toString(),
                                "id",
                                packageName
                            )
                        ).isVisible = true
                    }
                    mPrincessViewModel.clear()
                    mRun.mMap.clear()
                    mRun.mPrincess.clear()
                    mCodeBlockViewModel.isRunning.value = false
                }

                PLAYER_WIN -> {  // 승리
                    binding.tvWin.isVisible = true
                    val intent = Intent()
                    Log.e("승리", "승리")
                    intent.putExtra("ok", "ok")
                    setResult(Activity.RESULT_OK, intent)
                }

                9 -> {  // 종료
                    finish()
                }

                PRINCESS_MOVE -> {
                    mPrincessViewModel.move(true)
                }

                PRINCESS_TURN -> {
                    mPrincessViewModel.move(false)
                }
            }
        })

        // 코드 실행 - 현재 실행 중인 블록의 배경 색칠하기
        mRun.nowProcessing.observe(this, Observer<Int> { t ->
            Log.e("현재 실행 위치", "$t")
            if (t > 8)
                rc_code_block_list.smoothScrollToPosition(t + 3)
            mCodeBlockViewModel.coloringNowProcessing(
                rc_code_block_list.findViewHolderForAdapterPosition(
                    t
                )
            )
        })

        // 코드 실행 - 현재 실행이 끝난 블록의 배경 끄기
        mRun.nowTerminated.observe(this, Observer<Int> { t ->
            mCodeBlockViewModel.coloringNowTerminated(
                rc_code_block_list.findViewHolderForAdapterPosition(t)
            )
        })

        mRun.princessAction.observe(this, Observer<Int> { t ->
            mBattleViewModel?.princessActionOn(t)
        })

        mRun.monsterAttack.observe(this, Observer<Int> { t ->
            mBattleViewModel?.bossAttackOn(t)
        })

        // 공주가 보스를 만남
        mRun.metBoss.observe(this, Observer<Boolean> { t ->
            //boss.text = if (t) "OFF" else "보스"
            constraintLayout.isVisible = !t
            bossField.isVisible = t

            if (t) {
                defineFightBoss.isVisible = true
                defineFightBossClose.isVisible = true

                if (stageNum == 51) {
                    this.mRun.mCodeBlock.value = arrayListOf(
                        CodeBlock("while(isAlive){", type = WHILE, argument = IS_ALIVE),
                        CodeBlock("    if(bossJumped()){", type = IF, argument = BOSS_JUMPED),
                        CodeBlock("        wait();"),
                        CodeBlock("        jump();"),
                        CodeBlock("    }"),
                        CodeBlock(
                            "    if(bossFistMoved()){",
                            type = IF,
                            argument = BOSS_FIST_MOVED
                        ),
                        CodeBlock(
                            "        if(bossFistDown()){",
                            type = IF,
                            argument = BOSS_FIST_DOWN
                        ),
                        CodeBlock("            for(3){", type = FOR, argument = 3),
                        CodeBlock("                jump();"),
                        CodeBlock("            }"),
                        CodeBlock("        }"),
                        CodeBlock("        if(bossPunch()){", type = IF, argument = BOSS_PUNCH),
                        CodeBlock("            dodge();"),
                        CodeBlock("        }"),
                        CodeBlock("    }"),
                        CodeBlock("    attack();"),
                        CodeBlock("}")
                    )
                }

                if (stageNum == 52) {
                    this.mRun.mCodeBlock.value = arrayListOf(
                        CodeBlock("while(isAlive) {", type = WHILE, argument = IS_ALIVE),
                        CodeBlock("    if(bossBlackhole()){", type = IF, argument = BOSS_BLACKHOLE),
                        CodeBlock(
                            "        while(isBlackhole()) {",
                            type = WHILE,
                            argument = IS_BLACKHOLE
                        ),
                        CodeBlock("             grabTight();"),
                        CodeBlock("        }"),
                        CodeBlock("    }"),
                        CodeBlock("    if(bossGreenHand()){", type = IF, argument = BOSS_GREENHAND),
                        CodeBlock("        readySpell(shield);", argument = SHIELD),
                        CodeBlock("        wandSpell();"),
                        CodeBlock("        shoutSpell();"),
                        CodeBlock("    }"),
                        CodeBlock("    readySpell(attack);", argument = ATTACK),
                        CodeBlock("    wandSpell();"),
                        CodeBlock("    shoutSpell();"),
                        CodeBlock("}")
                    )
                }
                //this.mRun.mCodeBlock.value = arrayListOf()
                mCodeBlockViewModel.adapter.notifyDataSetChanged()
                mCodeBlockViewModel.isRunning.value = false

                mBattleViewModel = BattleViewModel(
                    binding.hpBar,
                    binding.princess,
                    binding.monster,
                    binding.princessAttackMotion
                )

                mBattleViewModel!!.mRun = mRun
                mBattleViewModel!!.princessAction = stageInfo.princessAction
                mBattleViewModel!!.bossAction = stageInfo.bossAction
                mBattleViewModel!!.init()
                mBattleViewModel!!.playActivity = this

                mRun.mMonster = stageInfo.monster
                binding.battleVM = mBattleViewModel
                Toast.makeText(this, "보스를 만났어요", Toast.LENGTH_SHORT).show()
                mCodeBlockViewModel.isRunning.value = false
                mCodeBlockViewModel.setOfferedBlock(mMapSettingViewModel.bossBattleBlock!!)
            } else {
                defineFightBoss.visibility = View.GONE
                defineFightBossClose.visibility = View.GONE

                mBattleViewModel = null
                binding.battleVM = null
                mCodeBlockViewModel.setOfferedBlock(mMapSettingViewModel.offeredBlock)
                mCodeBlockViewModel.isRunning.value = false

                if (mRun.bossKilled) {
                    Toast.makeText(this, "보스를 물리쳤어요", Toast.LENGTH_SHORT).show()
                    mRun.mCodeBlock.value = arrayListOf()
                    mCodeBlockViewModel.adapter.notifyDataSetChanged()
                    mRun.mCodeBlock.value = mRun.backup
//
//                    for (i in mRun.backup!!) {
//                        Log.e(i.funcName, mRun.backIR.toString())
//                    }

                    mCodeBlockViewModel.adapter.notifyDataSetChanged()
                    if (mRun.mCodeBlock.value != null)
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

    override fun onStart() {
        super.onStart()
        val soundSource = resources.getIdentifier("act${this.stageNum / 10}", "raw", packageName)
        this.mp = MediaPlayer.create(this, soundSource)
        this.mp!!.isLooping = true
    }

    override fun onResume() {
        super.onResume()
        if (!isMute) mp!!.start()
    }

    override fun onPause() {
        super.onPause()
        mp!!.stop()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        Log.e("Layout Width - ", "Width" + (layoutMainView.width))
        this.mPrincessViewModel.setViewSize(layoutMainView.width)
        this.mMapSettingViewModel.setViewSize(layoutMainView.width)
        this.binding.mapSettingVM = this.mMapSettingViewModel
    }

    fun initStage(binding: ActivityPlayBinding) {
        this.layoutMainView = this.findViewById(R.id.constraintLayout)

        // Princess View Model과 bind
        binding.princessVM = this.mPrincessViewModel

        // Code Block View Model과 bind
        binding.codeBlockVM = this.mCodeBlockViewModel
        this.mCodeBlockViewModel.init()
        this.mRun.init()

        this.stageInfo = this.mMapSettingViewModel.mMapSettingModel.getStageInfo(this.stageNum)
        val drawableInfo = this.stageInfo.map.drawables!!
        this.mMapSettingViewModel.setStage(this.stageInfo, this)
        this.mPrincessViewModel.setPrincessImage(drawableInfo, ivPrincess, ivAxe, tvWin)
        this.mCodeBlockViewModel.setSettingModel(drawableInfo)
        this.mCodeBlockViewModel.setOfferedBlock(this.mMapSettingViewModel.offeredBlock)

        binding.mapSettingVM = this.mMapSettingViewModel
        Log.e("layout mapSettingVM", "${this.mMapSettingViewModel.oneBlock.value}")

        this.mRun.mMap = this.stageInfo.map
        this.mRun.mPrincess = this.stageInfo.princess
        this.mRun.mClearCondition = this.stageInfo.clearCondition
        this.mRun.mPrincessViewModel = this.mPrincessViewModel
        this.mRun.mCodeBlockViewModel = this.mCodeBlockViewModel
    }
}