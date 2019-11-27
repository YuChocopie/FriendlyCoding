package com.mashup.friendlycoding.viewmodel

import android.os.Build
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import com.mashup.friendlycoding.R
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

        else if (type == 6) {
            val ani = TranslateAnimation(0F, 0F, 0F, -200F)
            ani.duration = 500
            ani.fillAfter = false
            playActivity.princess.startAnimation(ani)
        }

        else if (type == 3) {
            val ani = TranslateAnimation(0F, 100F, 0F, 0F)
            ani.duration = 500
            ani.fillAfter = false
            playActivity.princess.startAnimation(ani)
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
            val ani = TranslateAnimation(0F, 0F, 0F, -500F)
            ani.duration = 500
            ani.fillAfter = false
            ani.startOffset = 500
            playActivity.boss_attack.startAnimation(ani)
        }

        if (type == -1) {
            playActivity.boss_attack.isVisible = false
            if (mRun.mMonster!!.type == 2) {
                playActivity.monster.setImageResource(R.drawable.monster2)
            }
            else if (mRun.mMonster!!.type == 3) {
                playActivity.monster.setImageResource(R.drawable.monster3)
            }
        }

        else if (type in 3..5 || type == 7) {
            Log.e("보스 이미지를", "바꿉니다")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                playActivity.monster.setImageDrawable(playActivity.getDrawable(bossAction!![type]))
            }
            playActivity.monster.isVisible = true

            if (type == 4) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    playActivity.boss_attack.setImageDrawable(playActivity.getDrawable(bossAction!![2]))
                }
                playActivity.boss_attack.isVisible = true
                val ani = TranslateAnimation(0F, 200F, 0F, 0F)
                ani.duration = 500
                ani.fillAfter = false
                ani.repeatCount = 2
                ani.startOffset = 750
                playActivity.boss_attack.startAnimation(ani)
            }
        }

        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                playActivity.boss_attack.setImageDrawable(playActivity.getDrawable(bossAction!![type]))
            }
            playActivity.boss_attack.isVisible = true
        }
    }
}