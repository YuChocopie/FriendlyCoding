package com.mashup.friendlycoding.model

import android.app.Application
import com.mashup.friendlycoding.viewmodel.BattleViewModel
import kotlin.random.Random

class Monster (val maxHP : Int) {
    var currentHP = maxHP

    private val FIRE = 0
    private val EARTH = 1

    private var attackType : Int = 0

}