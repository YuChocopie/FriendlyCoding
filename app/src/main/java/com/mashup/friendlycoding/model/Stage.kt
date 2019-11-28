package com.mashup.friendlycoding.model

import com.mashup.friendlycoding.Monster
import com.mashup.friendlycoding.Princess

class Stage(val map : Map,
            val princess : Princess,
            val monster: Monster?,
            val offeredBlock : ArrayList<CodeBlock>,
            val bossBattleBlock : ArrayList<CodeBlock>? = null,
            val clearCondition : (Princess) -> Boolean,
            val princessAction : ArrayList<Int>? = null,
            val bossAction : ArrayList<Int>? = null
            )