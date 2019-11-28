package com.mashup.friendlycoding.model

import com.mashup.friendlycoding.Monster
import com.mashup.friendlycoding.Princess

class Stage(val map : Map,
            val princess : Princess,
            val offeredBlock : ArrayList<CodeBlock>,
            val clearCondition : (Princess) -> Boolean,
            val monster: Monster? = null,
            val bossBattleBlock : ArrayList<CodeBlock>? = null,
            val princessAction : ArrayList<Int>? = null,
            val bossAction : ArrayList<Int>? = null
            )