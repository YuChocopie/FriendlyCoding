package com.mashup.friendlycoding.viewmodel

import android.os.Build
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import com.mashup.friendlycoding.activity.PlayActivity
import com.mashup.friendlycoding.model.RunModel
import kotlinx.android.synthetic.main.activity_play.*

class BattleViewModel(private val hpBar : View, var princessImg : View, var monsterImg : View, var princessAttackImg : View) : ViewModel() {
    var mRun = RunModel()
    var princessAction : ArrayList<Int>? = null
    var bossAction : ArrayList<Int>? = null
    private lateinit var param : ConstraintLayout.LayoutParams
    lateinit var playActivity : PlayActivity

    fun monsterAttacked() {
        param.marginEnd += 100
        hpBar.layoutParams = param
        princessAttackImg.isVisible = true
    }

    fun init() {
        param = hpBar.layoutParams as ConstraintLayout.LayoutParams
    }

    fun princessActionOn (type : Int) {
        if (type == -1) {
            playActivity.shield.isVisible = false
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                playActivity.shield.setImageDrawable(playActivity.getDrawable(princessAction!![type]))
            }
            playActivity.shield.isVisible = true
        }
    }

    fun bossAttackOn (type : Int) {
        if (type == 2) {
            return
        }

        if (type == -1) {
            playActivity.boss_attack.isVisible = false
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                playActivity.boss_attack.setImageDrawable(playActivity.getDrawable(bossAction!![type]))
            }
            playActivity.boss_attack.isVisible = true
        }
    }
}