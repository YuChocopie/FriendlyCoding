package com.mashup.friendlycoding.model

import android.util.Log
import com.mashup.friendlycoding.ignoreBlanks
import kotlin.random.Random

class RunModel : RunBaseModel() {
    var turnOff : Int = 0
    var result : Int = 0

    fun collisionCheck(): Int {   // 벽이나 보스와의 충돌 감지
        if (x < 10 && x > -1 && y < 10 && y > -1) {
            if (mMap.mapList!![y][x] == WALL) {
                // 벽이라면 졌다는 시그널 전송
                return PLAYER_LOST
            }

            else if (mMap.mapList!![y][x]%10 == CLEAR) {
                // 이겼다면 이겼다는 시그널 전송
                return (if(mClearCondition!!(mPrincess)) PLAYER_WIN else PLAYER_LOST)
            }
        }

        else {
            moveView.postValue(PLAYER_LOST)     // 인덱스를 넘어갈 시
            return PLAYER_LOST
        }
        return 0
    }

    fun run(mapDrawable: MapDrawable) {
        iterator = 0
        compileError = false
        if (!bossKilled) {
            if (first) {
                x = mapDrawable.princessX
                y = mapDrawable.princessY
                first = false
            }
        }

        Log.e("괄호", "isEmpty : ${bracketStack.empty()}")
        if (bracketStack.isNotEmpty() || closingBracket != openingBracket) {
            moveView.postValue(COMPILE_ERROR)
            return
        }

        IR = backIR
        if (!bossKilled) {
            IR = 0
        }

        val run = RunThead()
        var open = 0

        while (open < mCodeBlock.value!!.size) {
            if (mCodeBlock.value!![open].type != 0) {
                open = compile(open)
            }
            open++
        }

        if (compileError) {
            moveView.postValue(COMPILE_ERROR)
        }
        else {
            mCodeBlockViewModel.isRunning.value = true
            run.start()
            mCodeBlockViewModel.isRunning.value = false
        }

        resultExecution()
    }

    fun resultExecution() {
        if (result == 0) {
            return
        }

        else if (result == LOST_BOSS_BATTLE || result == PLAYER_LOST) {
            mCodeBlockViewModel.clearBlock()
            mPrincessViewModel.clear()
        }
    }

    inner class RunThead : Thread() {
        override fun run() {
            try {
                moveView.postValue(START_RUN)
                sleep(speed)

                while (IR < mCodeBlock.value!!.size) {
                    if (isAttacking) {
                        Log.e("몬스터 공격 중", "   ")
                    }

                    if (metBoss.value == true && !isAttacking && mMonster!!.isAlive()) {
                        // 몬스터의 차례
                        mMonster?.attack()
                        Log.e("몬스터의 공격!", "${mMonster?.attackType}")
                        monsterAttack.postValue(mMonster?.attackType)
                        if (mMonster?.attackType != -1) {
                            when (mMonster?.attackType) {
                                BOSS_FIRE_ATTACK -> Log.e("몬스터의", "불 공격!!!")
                                BOSS_WATER_ATTACK -> Log.e("몬스터의", "물 공격!!!")
                                BOSS_FIST_MOVED -> Log.e("몬스터의", "주먹 들기!!!")
                                BOSS_FIST_DOWN -> Log.e("몬스터의", "주먹 내려치기!!!")
                                BOSS_PUNCH -> Log.e("몬스터의", "펀치!!!")
                                BOSS_JUMPED -> Log.e("몬스터의", "점프!!!")
                                BOSS_BLACKHOLE -> Log.e("몬스터의", "블랙홀 생성!!!")
                                BOSS_GREENHAND -> Log.e("몬스터의", "초록손!!!")
                            }
                            sleep(speed)

                            if (coc[mMonster?.attackType!!] == -1) {   // 피하는 루틴이 없음
                                moveView.postValue(LOST_BOSS_BATTLE)   // 사망
                                result = LOST_BOSS_BATTLE
                                return
                            } else {
                                IR = coc[mMonster?.attackType!!]  // 해당하는 것을 막으러 가자.
                                isAttacking = true
                            }
                        } else {
                            Log.e("몬스터", "휴식!")
                        }
                    }

                    if (iterator > 30) {
                        // 이런 게임 깨는데 루프를 30번 넘게 돌진 않겠지?
                        moveView.postValue(INFINITE_LOOP)
                        return
                    }

                    Log.e("실행 중 : ", mCodeBlock.value!![IR].funcName + " ${mCodeBlock.value!![IR].type}")
                    nowProcessing.postValue(IR)
                    turnOff = IR

                    when (ignoreBlanks(mCodeBlock.value!![IR].funcName)) {
                        // TODO : 0 유형 블록 (일반 함수)
                        /***
                         * 예)
                         * "함수이름" -> {
                         * ...
                         * sleep(speed)
                         * }
                         * ****/
                        "move();" -> {
                            movePrincess()
                            moveView.postValue(PRINCESS_MOVE)
                            Log.e("현 위치", "y : $y, x : $x, 발밑 : ${mMap.mapList!![y][x]}")
                            val signal = collisionCheck()
                            if (signal != 0) {
                                sleep(speed)
                                moveView.postValue(signal)
                                if (signal == PLAYER_WIN) {
                                    sleep(speed)
                                    moveView.postValue(9)
                                }
                                nowTerminated.postValue(IR)
                                return
                            }
                        }

                        "turnLeft();" -> {
                            rotate(false)
                            moveView.postValue(PRINCESS_TURN)
                        }

                        "turnRight();" -> {
                            rotate(true)
                            moveView.postValue(PRINCESS_TURN)
                        }

                        "for(" -> {
                            iterator = mCodeBlock.value!![IR].argument
                            iteratorStack.push(mCodeBlock.value!![IR].argument)
                            Log.e("반복", "${mCodeBlock.value!![IR].argument}")
                        }

                        "}" -> {
                            turnOff = IR
                            jumpTo = mCodeBlock.value!![IR].address
                            Log.e("오프너", mCodeBlock.value!![jumpTo].funcName)
                            Log.e("jumpTo", "$jumpTo, $iterator to ${mCodeBlock.value!![jumpTo].argument} , ${ignoreBlanks(mCodeBlock.value!![jumpTo].funcName)}")

                            if (mCodeBlock.value!![jumpTo].type == 4) {
                                Log.e("요건", "while문")
                                IR = jumpTo - 1
                            }

                            if (mMonster != null && mCodeBlock.value!![jumpTo].type == 2 && isAttacking && mCodeBlock.value!![jumpTo].argument == mMonster!!.attackType) {
                                Log.e("몬스터", "공격 종료!, ${mCodeBlock.value!![jumpTo].funcName}")
                                monsterAttack.postValue(-1)
                                if (mCodeBlock.value!![jumpTo].argument != BOSS_FIST_MOVED || mCodeBlock.value!![jumpTo].argument != BOSS_PUNCH || mCodeBlock.value!![jumpTo].argument != BOSS_BLACKHOLE)
                                    Log.e("다시 공격 가능", "다시")
                                    isAttacking = false
                            }

                            if (mCodeBlock.value!![jumpTo].type == 1) {
                                Log.e("for 가 날 열었어", "$jumpTo 의  ${mCodeBlock.value!![jumpTo].argument}")
                                if (mCodeBlock.value!![jumpTo].argument-- > 1) { nowTerminated.postValue(IR)
                                    IR = jumpTo
                                    Log.e("한 번 더!", "${mCodeBlock.value!![jumpTo].argument}   ${mCodeBlock.value!![IR].funcName}")
                                    iterator++
                                }

                                else {
                                    //mCodeBlock.value!![jumpTo].argument = iterator
                                    //mCodeBlock.value!![IR].argument = iterator
                                    mCodeBlock.value!![jumpTo].argument = iteratorStack.peek()
                                    iteratorStack.pop()
                                }
                            }
                        }

                        // 아이템 습득 부분
                        "pickAxe();" -> {
                            itemPick(PICKAXE, mPrincess::pickAxe)
                        }

                        "eatMushroom();" -> {
                            itemPick(MUSHROOM, mPrincess::eatMushroom)
                        }

                        "pickBook();" -> {
                            itemPick(BOOK, mPrincess::pickBook)
                        }

                        "pickBranch();" -> {
                            itemPick(BRANCH, mPrincess::pickBranch)
                        }

                        // 보스전 부분
                        "fightBoss();" -> {
                            if (mMap.mapList!![y][x]%10 == BOSS) {
                                backup = arrayListOf()
                                backup!!.addAll(mCodeBlock.value!!)
                                backIR = IR + 1
                                nowTerminated.postValue(turnOff)
                                blockLevel++
                                metBoss.postValue(true)
                                //mBossBattleRunModel = BossBattleRunModel()
                                //mCodeBlockViewModel.adapter.notifyDataSetChanged()
                                return
                            }
                        }

                        // 보스 1 (파랑 마법사)
                        "attack();" -> {
                            mMonster?.monsterAttacked(mPrincess.DPS)
                            monsterAttacked.postValue(true)
                        }

                        "fireShield();" -> {
                            princessAction.postValue(0)
                        }

                        "iceShield();" -> {
                            princessAction.postValue(1)
                        }

                        // 보스 2 (회색 공룡)
                        "wait();" -> {
                            // 진짜 아무것도 안 한다.
                            Log.e("공주", "대기!!!")
                        }

                        "jump();" -> {
                            Log.e("공주", "점프!!!")
                            if (bossAttackIterator > 0) {
                                bossAttackIterator--
                            }
                            // TODO : 점프 애니메이션
                        }

                        "dodge();" -> {
                            // TODO : 피하는 애니메이션
                            Log.e("공주", "피하기!!!")
                        }

                        // 보스 3 (악마왕)
                        "grabTight();" -> {
                            // TODO : 기둥 꽉 잡는 애니메이션
                            Log.e("공주", "꽉 잡아!!!")
                        }

                        "wandSpell();" -> {
                            if (spellSequence != 2)
                                moveView.postValue(LOST_BOSS_BATTLE)
                            else {
                                // TODO : 마법봉에 주문을 건다
                                Log.e("공주", "마법봉!!!")
                                spellSequence--
                            }
                        }

                        "shoutSpell();" -> {
                            if (spellSequence != 1)
                                moveView.postValue(LOST_BOSS_BATTLE)
                            else {
                                // TODO : 마법을 외친다
                                Log.e("공주", "외침!!!")
                                spellSequence = 3
                            }
                        }

                        else -> {
                            if (mCodeBlock.value!![IR].funcName.length > 10) {
                                if (mCodeBlock.value!![IR].funcName.substring(0, 10) == "readySpell") {
                                    if (spellSequence != 3)
                                        moveView.postValue(LOST_BOSS_BATTLE)
                                    else {
                                        // TODO : 주문 준비 모션
                                        spellSequence--
                                    }
                                }
                            }

                            if (mCodeBlock.value!![IR].type == 2) {
                                Log.e("if", "입니다, ${mCodeBlock.value!![IR].argument}")

                                if (mCodeBlock.value!![IR].argument <= 8) { // 보스
                                    if (mMonster != null) {
                                        if (isAttacking && mMonster!!.type == 2 && mMonster!!.attackType == BOSS_FIST_MOVED) {
                                            mMonster!!.attackType = rand.nextInt(2) + 4
                                            Log.e("몬스터!!", if (mMonster!!.attackType == BOSS_FIST_DOWN) "내려찍기" else "펀치")

                                            if (coc[mMonster!!.attackType] <= -1) {
                                                // 해당 하는 방어 수단 없음
                                                moveView.postValue(LOST_BOSS_BATTLE)
                                            }
                                            else if (coc[mMonster!!.attackType] < IR || coc[mMonster!!.attackType] > mCodeBlock.value!![IR].address) {
                                                // 잘못된 if 접근
                                                moveView.postValue(LOST_BOSS_BATTLE)
                                            }
                                            else {
                                                Log.e("막으러 가자", mCodeBlock.value!![coc[mMonster!!.attackType]].funcName)
                                                IR = coc[mMonster!!.attackType]
                                            }
                                        }

                                        else if (isAttacking && mMonster!!.type == 3 && mMonster!!.attackType == BOSS_BLACKHOLE) {
                                            bossAttackIterator = rand.nextInt(5) + 3
                                        }

                                        else if (isAttacking && mMonster!!.type == 2 && mMonster!!.attackType == BOSS_JUMPED) {
                                            bossAttackIterator = 3
                                        }

                                        if (isAttacking && (mMonster!!.attackType == mCodeBlock.value!![IR].argument)) {
                                            Log.e("막았다!", "${mCodeBlock.value!![jumpTo].argument} 공격")

                                            if (!defenseSuccess(mMonster!!.attackType)) { // 공격 중이고, 해당하는 수단 있으나, 잘못된 방어 패턴
                                                Log.e("아니네!", "${mCodeBlock.value!![jumpTo].argument} 공격")
                                                moveView.postValue(LOST_BOSS_BATTLE)
                                            }
                                        }

                                        else { // 해당하는 방어, 회피 수단 있으나 아직 공격 중이지 않음.
                                            IR = mCodeBlock.value!![IR].address
                                        }
                                    }
                                }

                                else {
                                    if (!type3Function(mCodeBlock.value!![IR].argument)(mPrincess)) {
                                        Log.e("분기", "${mCodeBlock.value!![IR].address}로!")
                                        IR = mCodeBlock.value!![IR].address
                                    }
                                }
                            }

                            else if (mCodeBlock.value!![IR].type == 4) { // while
                                jumpTo = mCodeBlock.value!![IR].address
                                when (mCodeBlock.value!![IR].argument) {
                                    IS_ALIVE -> {   // isAlive
                                        if (!mMonster!!.isAlive()) {
                                            IR = jumpTo
                                            Log.e("죽었네!", "$jumpTo 로!")
                                            bossKilled = true
                                            nowTerminated.postValue(turnOff)
                                            metBoss.postValue(false)
                                            iterator = 0
                                            return
                                        } else {
                                            iterator++
                                        }
                                    }

                                    IS_BLACKHOLE -> {
                                        if (bossAttackIterator <= 0) {
                                            IR = jumpTo
                                            Log.e("블랙홀 끝!!", "$jumpTo 로!")
                                        }

                                        else {
                                            bossAttackIterator--
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (bossAttackIterator > 0 && mCodeBlock.value!![turnOff].type == 0) {
                        if (ignoreBlanks(mCodeBlock.value!![turnOff].funcName) != "grabTight();" || ignoreBlanks(mCodeBlock.value!![turnOff].funcName) != "jump();") {
                            Log.e("블랙홀, 점프 파동", "진행 중")
                            Log.e("근데 ", ignoreBlanks(mCodeBlock.value!![turnOff].funcName))
                            moveView.postValue(LOST_BOSS_BATTLE)
                        }
                    }

                    sleep(speed)
                    nowTerminated.postValue(turnOff)
                    princessAction.postValue(-1)
                    monsterAttacked.postValue(false)
                    IR++  // PC
                }
                moveView.postValue(-3)
            } catch (e: IndexOutOfBoundsException) {
                return
            }
        }
    }
}