package com.smu.friendlycoding.model

import com.smu.friendlycoding.Monster
import com.smu.friendlycoding.Princess

class Stage(val map : Map,
            val princess : Princess,
            val offeredBlock : ArrayList<CodeBlock>,
            val clearCondition : (Princess) -> Boolean,
            val monster: Monster? = null,
            val bossBattleBlock : ArrayList<CodeBlock>? = null,
            val princessAction : ArrayList<Int>? = null,
            val bossAction : ArrayList<Int>? = null
            )