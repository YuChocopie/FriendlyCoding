package com.mashup.friendlycoding.model

import android.util.Log
import com.mashup.friendlycoding.ignoreBlanks

class RunModel : RunBaseModel() {
    var turnOff: Int = 0
    var result: Int = 0

    fun collisionCheck(): Int {   // 벽이나 보스와의 충돌 감지
        if (x < 10 && x > -1 && y < 10 && y > -1) {
            Log.e("충돌아직!! 원인은?!", "${mMap.mapList!![y][x]}")
            if ((mMap.mapList!![y][x] == WALL) || (mMap.mapList!![y][x] % 10 == ROCK)) {
                Log.e("충돌발생!! 원인은?!", "${mMap.mapList!![y][x]}")
                // 돌 또는 벽이라면 졌다는 시그널 전송
                return PLAYER_LOST
            } else if (mMap.mapList!![y][x] % 10 == CLEAR) {
                // 이겼다면 이겼다는 시그널 전송
                return (if (mClearCondition!!(mPrincess)) PLAYER_WIN else PLAYER_LOST)
            }
        } else {
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
        } else {
            mCodeBlockViewModel.isRunning.value = true
            run.start()
            mCodeBlockViewModel.isRunning.value = false
        }

        resultExecution()
    }

    fun resultExecution() {
        if (result == 0) {
            return
        } else if (result == LOST_BOSS_BATTLE || result == PLAYER_LOST) {
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
                    if (metBoss.value == true && !isAttacking && mMonster!!.isAlive()) {
                        // 몬스터의 차례
                        mMonster?.attack()
                        Log.e("몬스터의 공격!", "${mMonster?.attackType}")
                        monsterAttack.postValue(mMonster?.attackType)
                        if (mMonster?.attackType != -1) {
                            when (mMonster?.attackType) {
                                BOSS_FIRE_ATTACK -> Log.e("몬스터의", "불 공격!!!")
                                BOSS_WATER_ATTACK -> Log.e("몬스터의", "물 공격!!!")
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

                    nowProcessing.postValue(IR)
                    turnOff = IR
                    Log.e(
                        "실행 중 : ",
                        mCodeBlock.value!![IR].funcName + " ${mCodeBlock.value!![IR].type}"
                    )
                    Log.e("퍼네임", "${ignoreBlanks(mCodeBlock.value!![IR].funcName)}")
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
                                    moveView.postValue(9)//finish
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
                            Log.e(
                                "jumpTo",
                                "$jumpTo, $iterator to ${mCodeBlock.value!![jumpTo].argument} , ${ignoreBlanks(
                                    mCodeBlock.value!![jumpTo].funcName
                                )}"
                            )

                            if (mCodeBlock.value!![jumpTo].type == 4) {
                                Log.e("요건", "while문")
                                IR = jumpTo - 1
                            }

                            if (mMonster != null && mCodeBlock.value!![jumpTo].type == 2 && isAttacking && mCodeBlock.value!![jumpTo].argument == mMonster!!.attackType) {
                                Log.e("몬스터", "공격 종료!")
                                monsterAttack.postValue(-1)
                                isAttacking = false
                            }

                            if (mCodeBlock.value!![jumpTo].type == 1) {
                                Log.e(
                                    "for 가 날 열었어",
                                    "$jumpTo 의  ${mCodeBlock.value!![jumpTo].argument}"
                                )
                                if (mCodeBlock.value!![jumpTo].argument-- > 1) {
                                    nowTerminated.postValue(IR)
                                    IR = jumpTo
                                    Log.e(
                                        "한 번 더!",
                                        "${mCodeBlock.value!![jumpTo].argument}   ${mCodeBlock.value!![IR].funcName}"
                                    )
                                    iterator++
                                } else {
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

                        "crushRock();" -> {
                            val e = Log.e("Rock", "andRoll")
                            if (mPrincess.isPickAxe){
                                cruchRock(ROCK, mPrincess::crushRock)
                            }
                            Log.e("Rock1", "andRoll222")
                        }
                        // 보스전 부분
                        "fightBoss();" -> {
                            if (mMap.mapList!![y][x] % 10 == BOSS) {
                                backup = arrayListOf()
                                backup!!.addAll(mCodeBlock.value!!)
                                backIR = IR + 1
                                nowTerminated.postValue(turnOff)
                                blockLevel++
                                metBoss.postValue(true)
                                //mCodeBlockViewModel.adapter.notifyDataSetChanged()
                                return
                            }
                        }

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

                        else -> {
                            if (mCodeBlock.value!![IR].type == 2) {
                                Log.e("if", "입니다, ${mCodeBlock.value!![IR].argument}")

                                if (mCodeBlock.value!![IR].argument == 0 || mCodeBlock.value!![IR].argument == 1) { // 보스
                                    if (mMonster != null) {
                                        if (isAttacking && (mMonster!!.attackType == mCodeBlock.value!![IR].argument)) {
                                            Log.e(
                                                "막았다!",
                                                "${mCodeBlock.value!![jumpTo].argument} 공격"
                                            )
                                        } else {
                                            IR = mCodeBlock.value!![IR].address
                                        }
                                    }
                                } else {
                                    if (!type3Function(mCodeBlock.value!![IR].argument)(mPrincess)) {
                                        Log.e("분기", "${mCodeBlock.value!![IR].address}로!")
                                        IR = mCodeBlock.value!![IR].address
                                    }
                                }
                            } else if (mCodeBlock.value!![IR].type == 4) { // while
                                jumpTo = mCodeBlock.value!![IR].address
                                when (mCodeBlock.value!![IR].argument) {
                                    7 -> {   // isAlive
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
                                }
                            }
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