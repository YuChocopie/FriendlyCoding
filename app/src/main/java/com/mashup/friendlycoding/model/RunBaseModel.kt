package com.mashup.friendlycoding.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mashup.friendlycoding.Monster
import com.mashup.friendlycoding.Princess
import com.mashup.friendlycoding.ignoreBlanks
import com.mashup.friendlycoding.viewmodel.CodeBlockViewModel
import com.mashup.friendlycoding.viewmodel.PrincessViewModel
import java.util.*
import kotlin.collections.ArrayList

open class RunBaseModel {
    // PrincessViewModel
    lateinit var mPrincessViewModel: PrincessViewModel
    var moveView =
        MutableLiveData<Int>()    // MainActivity에게 보내는 시그널 - 진행 중 상황. 코드 실행의 시작, 종료, 공주의 움직임 등.

    // CodeBlockViewModel
    lateinit var mCodeBlockViewModel: CodeBlockViewModel
    var nowProcessing = MutableLiveData<Int>()   // MainActivity에게 보내는 시그널 - 현재 진행 중인 코드 번호
    var nowTerminated = MutableLiveData<Int>()   // MainActiivty에게 보내는 시그널 - 현재 진행 종료된 코드 번호
    var mCodeBlock =
        MutableLiveData<ArrayList<CodeBlock>>()    // 코드 블록, MainActivity가 보고 뷰의 수정과 스크롤이 일어남
    var insertBlockAt = MutableLiveData<Int>()  // MainActivity에게 보내는 시그널 - 코드 블록이 어디에 삽입될 지를 알려준다.

    // BattleViewModel
    var metBoss =
        MutableLiveData<Boolean>()  // MainActivity에게 보내는 시그널 - 플레이어가 보스를 만났는지 여부. 만났으면 뷰와 인풋코드블록을 바꾼다.
    var monsterAttack = MutableLiveData<Int>()  // MainActivity에게 보내는 시그널 - 보스의 공격 유형
    var princessAction = MutableLiveData<Int>() // MainActivity에게 보내는 시그널 - 보스전에서의 공주의 행동
    var monsterAttacked = MutableLiveData<Boolean>()    // MainActivity에게 보내는 시그널 - 보스가 공격당했는지 여부

    // 보스전 시 백업될 코드 블록 리스트
    var backup: ArrayList<CodeBlock>? = null
    var backIR: Int = 0
    var bossKilled: Boolean = false

    // 공주의 좌표
    var x = 0 // x좌표
    var y = 9 // y좌표

    var mPrincess = Princess()
    var mMap = Map()
    var mMonster: Monster? = null

    var insertBlockPosition = 0
    var insertedBlock: String? = null   // 삽입된 코드 블록의 이름
    var changingView: Int = 0    // 아이템을 주웠거나 아이템이 파괴됐을시 해당 아이템의 ID를 MainActivity에게 알려준다.

    var jumpTo = 0 // jump 할 주소
    var IR = 0  // 명령어 실행할 주소
    var iterator = 0 // 반복자
    var blockLevel = 0 // 들여쓰기 정도.
    var bracketStack = Stack<Int>()  // 괄호 체크, 그와 동시에 jump 할 명령어 주소 얻기 위함
    var coc = arrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1) // 행동 수칙이 있는가?
    var isAttacking = false  // 몬스터가 공격 중에 있는지
    var isBossAlive = false
    var speed = 500L
    var iteratorStack = Stack<Int>()
    var compileError: Boolean = false

    var openingBracket = 0
    var closingBracket = 0
    var first = true

    val rand = Random()
    var spell = -1
    var bossAttackIterator = 0
    var spellSequence = 3

    // 클리어 조건
    var mClearCondition: ((Princess) -> Boolean)? = null
    var stageClear = false

    /***
     * inti()
     * ***/
    fun init() {
        mCodeBlock.value = arrayListOf()
        insertBlockPosition = -1
        insertBlockAt.postValue(-1)
        metBoss.value = false
        monsterAttack.value = -1
        princessAction.value = -1
        compileError = false
        IR = 0
        nowProcessing.value = -1
    }

    /***
     * 공주 이동 관련 코드
     * ***/
    fun movePrincess() {
        mPrincessViewModel.direction %= 4
        when (mPrincessViewModel.direction) {
            //goint up
            0 -> y--
            //going right
            1 -> x++
            //going down
            2 -> y++
            //going left
            3 -> x--
        }
    }

    fun rotate(LeftOrRight: Boolean) {
        if (!LeftOrRight) { // 왼쪽으로
            if (mPrincessViewModel.direction > 0)
                mPrincessViewModel.direction -= 1
            else
                mPrincessViewModel.direction = 3
        } else {  // 오른쪽으로
            if (mPrincessViewModel.direction < 3)
                mPrincessViewModel.direction++
            else
                mPrincessViewModel.direction = 0
        }
    }

    /***
     * 코드블락 관련 코드
     * ***/
    fun clearBlock() {
        //mPrincessViewModel.clear()
        first = true
        bossKilled = false
        x = mMap.startX
        y = mMap.startY
        //mPrincessViewModel.direction = 1
        iterator = 0
        jumpTo = 0
        blockLevel = 0
        moveView.postValue(-1)
        insertBlockAt.value = -1
        val block = mCodeBlock.value
        block!!.clear()
        bracketStack.clear()
        mCodeBlock.postValue(block)
        nowTerminated.postValue(IR)
        IR = 0
        isBossAlive = false
        princessAction.value = -1
        compileError = false
        moveView.postValue(PLAYER_LOST)
    }

    fun changeBlockLevel(OpenOrClose: Boolean) {
        if (!OpenOrClose) {  // 여는 괄호를 삭제함
            Log.e("여는 괄호", "삭제")
            blockLevel--
            openingBracket--
            if (bracketStack.isNotEmpty()) {
                bracketStack.pop()
            }
        } else {
            Log.e("닫는 괄호", "삭제")
            blockLevel++  // 닫는 괄호를 삭제함
            closingBracket--
            bracketStack.push(1)
        }
    }

    private fun insertBlock(at: String, block: String): String {
        var idx = 0
        val inserted: String
        while (at[idx++] != '(') {
        }
        inserted = at.substring(0, idx) + block + ')'
        return inserted
    }

    fun addNewBlock(codeBlock: CodeBlock) {
        if (insertBlockPosition != -1) {
            if (codeBlock.type == 3 || codeBlock.funcName == "attack" || codeBlock.funcName == "shield") {
                Log.e("블록을 추가합니다", "${codeBlock.funcName}  ${codeBlock.type}  ${codeBlock.argument}")
                mCodeBlock.value!![insertBlockPosition].argument = codeBlock.argument
                insertedBlock = codeBlock.funcName
                mCodeBlock.value!![insertBlockPosition].funcName =
                    insertBlock(mCodeBlock.value!![insertBlockPosition].funcName, insertedBlock!!)

                if (codeBlock.type == 0)
                    mCodeBlock.value!![insertBlockPosition].funcName += ";"
                //insertBlockAt.postValue(insertBlockPosition)
                insertBlockPosition = -1
                Log.e("${codeBlock.funcName} ", "${insertBlockAt.value}에 추가됨")
                return
            } else
                return
        } else if (codeBlock.type == 3) {
            return
        }

        val adding = CodeBlock(codeBlock.funcName, address = IR, type = codeBlock.type, argument = codeBlock.argument)
        if (adding.funcName == "}") {
            if (bracketStack.empty()) {
                return
            }
            bracketStack.pop()
            Log.e("block level", "decreases")
            blockLevel--
            closingBracket++
        }

        var tap = ""
        for (i in 0 until blockLevel) {
            tap += "    "
        }

        adding.funcName = tap + adding.funcName

        if (adding.type != 0) {
            bracketStack.push(1)
            blockLevel++
            openingBracket++
        }

        val block = mCodeBlock.value
        block!!.add(adding)
        mCodeBlock.postValue(block)
        insertBlockAt.postValue(-1)

        Log.e(codeBlock.funcName, "${bracketStack.size}")
    }

    fun deleteBlock(position: Int) {
        if (mCodeBlock.value!![position].type != 0) {
            this.changeBlockLevel(false)

            for (i in position until mCodeBlock.value!!.size) {
                Log.e("코드 들이기", mCodeBlock.value!![i].funcName)
                if (ignoreBlanks(mCodeBlock.value!![i].funcName) == "}") {
                    break
                } else if (mCodeBlock.value!![i].funcName.substring(0, 4) == "    ") {
                    Log.e("코드 들이기", mCodeBlock.value!![i].funcName)
                    mCodeBlock.value!![i].funcName = mCodeBlock.value!![i].funcName.substring(4)
                }
            }
        } else if (ignoreBlanks(mCodeBlock.value!![position].funcName) == "}") {
            this.changeBlockLevel(true)
        }
        mCodeBlock.value!!.removeAt(position)

        Log.e("remove at $position", "${bracketStack.size}")
    }

    fun compile(open: Int): Int {
        var ir = open
        val myself = ir

        Log.e("여는 괄호?", mCodeBlock.value!![ir].funcName + "$ir")
        if ((mCodeBlock.value!![ir].type == 1 && mCodeBlock.value!![ir].argument <= 0) || ((mCodeBlock.value!![ir].type == 2 || mCodeBlock.value!![ir].type == 4) && mCodeBlock.value!![ir].argument < 0)) {
            compileError = true
        }

        ir++
        while (ir < mCodeBlock.value!!.size && ignoreBlanks(mCodeBlock.value!![ir].funcName) != "}") {
            if (mCodeBlock.value!![ir].type != 0) {
                ir = compile(ir)
            }
            ir++
        }
        Log.e("닫는 괄호?", mCodeBlock.value!![ir].funcName + " $myself" + " $ir 까지")
        mCodeBlock.value!![ir].address = myself

        if (mCodeBlock.value!![myself].type == 2 || mCodeBlock.value!![myself].type == 4) {
            if (mCodeBlock.value!![myself].type == 2)
                coc[mCodeBlock.value!![myself].argument] = myself
            mCodeBlock.value!![myself].address = ir
        }
        return ir
    }

    fun type3Function(num: Int) : Boolean {
        when (num) {
            IS_PICKAXE -> {
                return mMap.mapList!![y][x] % BASE == PICKAXE
            }

            IS_NOT_POISONED -> {
                return mMap.mapList!![y][x] % BASE == MUSHROOM
            }

            IS_BOOK -> {
                return mMap.mapList!![y][x] % BASE == BOOK
            }

            IS_NOT_BROKEN -> {
                return mMap.mapList!![y][x] % BASE == BRANCH
            }
            IS_ROCK -> {
                return mPrincess.isRock
            }
            IS_BAT -> {
                return mPrincess.isBat
            }
            else -> {
                return true
            }
        }
    }

    fun killBat(item: Int, f: () -> Int) {
        when (mPrincessViewModel.direction) {//바라보고있는 방향에 박쥐가 있을 경우
            0 -> {
                if (mMap.mapList!![y - 1][x] % BASE == item) {

                    val cnt = f()
                    changingView = mMap.mapList!![y - 1][x] / BASE
                    //mPrincessViewModel.itemCount.postValue(cnt.toString())
                    Log.e("cnt_killBat", "$cnt")

                    mPrincessViewModel.isItem.postValue("true")


                    mMap.itemPicked(y - 1, x)
                    moveView.postValue(KILL_BAT)
                } else {
                    moveView.postValue(PLAYER_LOST)
                    return
                }
            }
            1 -> {

                if (mMap.mapList!![y][x + 1] % BASE == item) {
                    Log.e("killBat_item", "${mMap.mapList!![y][x + 1] % BASE}")
                    val cnt = f()
                    Log.e("cnt_killBat", "$cnt")
                    changingView = mMap.mapList!![y][x + 1] / BASE
                    Log.e("changing", "$changingView")
                    //mPrincessViewModel.itemCount.postValue(cnt.toString())
                    Log.e("cnt", "$cnt")

                    mPrincessViewModel.isItem.postValue("true")

                    mMap.itemPicked(y, x + 1)
                    moveView.postValue(KILL_BAT)
                } else {
                    moveView.postValue(PLAYER_LOST)
                    return
                }
            }
            2 -> {
                if (mMap.mapList!![y + 1][x] % BASE == item) {

                    val cnt = f()
                    changingView = mMap.mapList!![y + 1][x] / BASE
                    //mPrincessViewModel.itemCount.postValue(cnt.toString())
                    Log.e("cnt", "$cnt")

                    mPrincessViewModel.isItem.postValue("true")

                    mMap.itemPicked(y + 1, x)
                    moveView.postValue(KILL_BAT)
                } else {
                    moveView.postValue(PLAYER_LOST)
                    return
                }
            }
            3 -> {
                if (mMap.mapList!![y][x - 1] % BASE == item) {
                    val cnt = f()
                    changingView = mMap.mapList!![y][x - 1] / BASE
                    //mPrincessViewModel.itemCount.postValue(cnt.toString())
                    Log.e("cnt", "$cnt")

                    mPrincessViewModel.isItem.postValue("true")

                    mMap.itemPicked(y, x - 1)
                    moveView.postValue(KILL_BAT)
                } else {
                    moveView.postValue(PLAYER_LOST)
                    return
                }
            }
        }
    }
    //TODO:cruchRock 함수 호출 시 itemCount의 값이 스테이지별로 전달되고 안되고 구분하기
    fun crushRock(item: Int, f: () -> Int) {
        when (mPrincessViewModel.direction) {//바라보고있는 방향에 돌이 존재 할 경우
            0 -> {
                if (mMap.mapList!![y - 1][x] % BASE == item) {
                    val cnt = f()
                    changingView = mMap.mapList!![y - 1][x] / BASE

                    mPrincessViewModel.itemCount.postValue(cnt.toString())

                    if (cnt >= CRUSH_ROCK_COUNT) {
                        mPrincessViewModel.isItem.postValue("true")
                    }
                    Log.e("삭제전","ㅇㅇ")
                    mMap.itemPicked(y - 1, x)
                    Log.e("삭제 후", "${y-1}, $x")
                    moveView.postValue(CRUSH_ROCK)
                } else {
                    moveView.postValue(PLAYER_LOST)
                    return
                }
            }
            1 -> {

                if (mMap.mapList!![y][x + 1] % BASE == item) {
                    val cnt = f()
                    changingView = mMap.mapList!![y][x + 1] / BASE
                    mPrincessViewModel.itemCount.postValue(cnt.toString())
                    if (cnt >= CRUSH_ROCK_COUNT) {
                        mPrincessViewModel.isItem.postValue("true")
                    }
                    Log.e("삭제전","ㅇㅇ")
                    mMap.itemPicked(y, x + 1)
                    Log.e("삭제 후", "${y-1}, $x")
                    moveView.postValue(CRUSH_ROCK)
                } else {
                    moveView.postValue(PLAYER_LOST)
                    return
                }
            }
            2 -> {
                if (mMap.mapList!![y + 1][x] % BASE == item) {

                    val cnt = f()
                    changingView = mMap.mapList!![y + 1][x] / BASE
                    mPrincessViewModel.itemCount.postValue(cnt.toString())
                    Log.e("cnt", "$cnt")
                    if (cnt >= CRUSH_ROCK_COUNT) {
                        mPrincessViewModel.isItem.postValue("true")
                    }
                    mMap.itemPicked(y + 1, x)
                    moveView.postValue(CRUSH_ROCK)
                } else {
                    moveView.postValue(PLAYER_LOST)
                    return
                }
            }
            3 -> {
                if (mMap.mapList!![y][x - 1] % BASE == item) {
                    val cnt = f()
                    changingView = mMap.mapList!![y][x - 1] / BASE
                    mPrincessViewModel.itemCount.postValue(cnt.toString())
                    Log.e("cnt", "$cnt")
                    if (cnt >= CRUSH_ROCK_COUNT) {
                        mPrincessViewModel.isItem.postValue("true")
                    }
                    mMap.itemPicked(y, x - 1)
                    moveView.postValue(CRUSH_ROCK)
                } else {
                    moveView.postValue(PLAYER_LOST)
                    return
                }
            }
        }
    }

    fun itemPick(item: Int, f: () -> Int) : Boolean {
        Log.e("아이템을 줍습니다.$y, $x", "공주 밑엔 ${mMap.mapList!![y][x]}")
        if (mMap.mapList!![y][x] % BASE == item) {
            //mPrincess.pickBranch()
            val cnt = f()
            changingView = mMap.mapList!![y][x] / BASE
            Log.e("chaaa", "${mMap.mapList!![y][x] % BASE}")
            //changingViewAll = mMap.mapList!![y][x]
            if (mMap.mapList!![y][x] % BASE != PICKAXE) {
                mPrincessViewModel.itemCount.postValue(cnt.toString())
                mPrincessViewModel.isItem.postValue("true")
            }

            mMap.itemPicked(y, x)
            moveView.postValue(ITEM_PICKED)
            return true
        } else {
            moveView.postValue(PLAYER_LOST)
            return false
        }
    }

    fun defenseSuccess(attackType: Int): Boolean {
        when (attackType) {
            BOSS_FIRE_ATTACK -> {
                return if (mCodeBlock.value!!.size > IR + 1) {
                    (ignoreBlanks(mCodeBlock.value!![IR + 1].funcName) == "iceShield();")
                } else
                    false
            }

            BOSS_WATER_ATTACK -> {
                return if (mCodeBlock.value!!.size > IR + 1) {
                    (ignoreBlanks(mCodeBlock.value!![IR + 1].funcName) == "fireShield();")
                } else
                    false
            }

            BOSS_JUMPED -> {
                Log.e("현재 코드 블록", "${this.mCodeBlock.value!!.size}, ${IR + 2}")
                return if (this.mCodeBlock.value!!.size > IR + 2) {
                    Log.e("피하는 방법?", ignoreBlanks(mCodeBlock.value!![IR + 2].funcName))
                    (ignoreBlanks(this.mCodeBlock.value!![IR + 2].funcName) == "jump();")
                } else
                    false
            }

            BOSS_FIST_MOVED -> {
                return true
            }

            BOSS_FIST_DOWN -> {
                return true
            }

            BOSS_PUNCH -> {
                Log.e("현재 코드 블록", "${this.mCodeBlock.value!!.size}, ${IR + 1}")
                return if (this.mCodeBlock.value!!.size > IR + 1) {
                    Log.e("피하는 방법?", ignoreBlanks(mCodeBlock.value!![IR + 1].funcName))
                    (ignoreBlanks(mCodeBlock.value!![IR + 1].funcName) == "dodge();")
                } else
                    false
            }

            BOSS_BLACKHOLE -> {
                return true
            }

            BOSS_GREENHAND -> {
                return true
            }

            else -> {
                return true
            }
        }
    }
}