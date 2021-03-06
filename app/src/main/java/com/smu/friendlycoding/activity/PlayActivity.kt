package com.smu.friendlycoding.activity

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
import com.smu.friendlycoding.R
import com.smu.friendlycoding.model.*
import com.smu.friendlycoding.viewmodel.BattleViewModel
import com.smu.friendlycoding.viewmodel.CodeBlockViewModel
import com.smu.friendlycoding.viewmodel.MapSettingViewModel
import com.smu.friendlycoding.viewmodel.PrincessViewModel
import kotlinx.android.synthetic.main.activity_play.*
import android.app.Dialog
import com.smu.friendlycoding.databinding.ActivityPlayBinding
import kotlinx.android.synthetic.main.tinkerbell.*
import java.util.*

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
    var stageNum: Int = 0
    lateinit var binding: ActivityPlayBinding
    lateinit var dialog: Dialog
    lateinit var tinkScript: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding =
            DataBindingUtil.setContentView<ActivityPlayBinding>(this, R.layout.activity_play)

        // 현재 몇 스테이지인지?
        this.stageNum = intent.getIntExtra("stageNum", 0)
        binding.lifecycleOwner = this
        initStage(binding)

        val intent = Intent(this, StoryActivity::class.java)
        intent.putExtra("stageNum", this.stageNum)
        startActivity(intent)

        Log.e("stageNum", "$stageNum")

        this.dialog = Dialog(this)
        this.dialog.setContentView(R.layout.tinkerbell)
        tinkSaying()

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
                binding.tvState.text = "isMushroom = "
                binding.tvCount.text = "mushroomCnt = "
            }

            23 -> {
                binding.tvCount.text = "branchCnt = "
                binding.tvState.text = "isBranch = "
            }

            31 -> {
                binding.tvCount.text = "rockCnt = "
                binding.tvState.text = "isRock = "
            }

            32 -> {
                binding.tvCount.text = "rockCnt = "
                binding.tvState.text = "isBat = "
            }

            33 -> {
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

        mRun.insertBlockAt.observe(this, Observer {
            if (it != -1) {
                Toast.makeText(this, "삽입할 조건 블록을 선택해주세요", Toast.LENGTH_SHORT).show()
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
                    //Toast.makeText(this, "클리어 실패", Toast.LENGTH_SHORT).show()
                    Log.e("실행 끝", "위로")
                    mRun.moveView.postValue(PLAYER_LOST)
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
                        if (mMapSettingViewModel.mDrawables.item[i].item_id % BASE == PICKAXE) {
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

                KILL_BAT -> {
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
                    mCodeBlockViewModel.coloringNowTerminated(
                        rc_code_block_list.findViewHolderForAdapterPosition(
                            mRun.IR
                        )
                    )
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
                    binding.codeBlockVM = this.mCodeBlockViewModel
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
            if (t > 0)
                rc_code_block_list.smoothScrollToPosition(t)
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

                this.mRun.mCodeBlock.value = arrayListOf()
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

        tink.setOnClickListener {
            this.dialog.setTitle("팅커벨")
            val rand = Random()
            this.dialog.tink_script.text = this.tinkScript[rand.nextInt(this.tinkScript.size)]
            this.dialog.show()
        }
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
        this.mPrincessViewModel.setPrincessImage(drawableInfo, this)
        this.mCodeBlockViewModel.setSettingModel(drawableInfo)
        this.mCodeBlockViewModel.setOfferedBlock(this.mMapSettingViewModel.offeredBlock)

        binding.mapSettingVM = this.mMapSettingViewModel
        Log.e("layout mapSettingVM", "${this.mMapSettingViewModel.oneBlock.value}")

        this.stageInfo.map.save()
        this.mRun.mMap = this.stageInfo.map
        this.mRun.mPrincess = this.stageInfo.princess
        this.mRun.mClearCondition = this.stageInfo.clearCondition
        this.mRun.mPrincessViewModel = this.mPrincessViewModel
        this.mRun.mCodeBlockViewModel = this.mCodeBlockViewModel
    }

    private fun tinkSaying() {
        this.tinkScript =
            when (this.stageNum) {
                11 -> arrayOf("포탈에 들어가면 통과할 수 있어!")
                12 -> arrayOf("왼쪽 오른쪽 방향을 잘 보자!", "모든 것엔 순서가 있어")
                13 -> arrayOf("왼쪽 오른쪽 방향을 잘 보자!", "공주의 방향을 예측해야 해", "틀렸다면, 어디서 틀렸는지 확인해 보자!")
                21 -> arrayOf("마법서는 언젠가 요긴하게 쓰일 거야!", "책을 주우면 위의 변수가 증가해!", "변수는 계산할 때 쓰자!")
                22 -> arrayOf("몇몇 버섯은 독버섯이야!", "독버섯인지 아닌지 확인을 하자", "if 를 써봐!")
                23 -> arrayOf("가지가 부러졌는지 확인해야해", "호수를 건너야 해", "가지가 부러졌을 예외를 생각해보자")
                31 -> arrayOf("광석은 한번에 깨지지가 않을거야", "한번 2번 연속으로 해볼까?")
                32 -> arrayOf("광석을 모두 깨지 않으면 포탈이 열리지 않아!", "박쥐가 비켜야 포탈을 이용 할 수 있겠지?")
                33 -> arrayOf("박쥐와 만나려면 어떻게 해야할까?", "광석을 캐볼까?")
                //...

                41 -> arrayOf("지금까지 배운 것을 활용해 봐", "순서에 맞게 해야 돼", "모든 것엔 순서가 있어")
                42 -> arrayOf("반복을 얼마나 할 지 잘 생각해 봐", "방향도 잘 계산해야 해", "코드는 거짓말을 하지 않아")
                43 -> arrayOf("물은 불로, 불은 물로 막아야 해", "그냥 공격은 안 돼. 보스를 물리칠 때까지야!")
                // ...

                51 -> arrayOf(
                    "조건을 잘 걸어야 해!",
                    "if 를 잘 써야 해",
                    "주먹을 든 후에, 내려치거나 펀치를 해",
                    "보스가 점프하면 한 템포 기다렸다 점프해"
                )
                52 -> arrayOf(
                    "모든 것엔 순서가 있어",
                    "공격/수비 주문을 준비하고, 마법봉에 건 뒤 외치는 거야",
                    "readySpell(); 을 클릭해 봐!",
                    "블랙홀이 언제까지 지속될지 몰라. 꽉 잡아야 해!"
                )
                else -> arrayOf("여기다가 설명을 넣으시오")
            }
    }


}