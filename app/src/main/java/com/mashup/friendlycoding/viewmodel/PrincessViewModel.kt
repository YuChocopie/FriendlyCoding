package com.mashup.friendlycoding.viewmodel

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.model.MapDrawable


class PrincessViewModel : ViewModel() {
    var itemCount = MutableLiveData<String>()
    var isItem = MutableLiveData<String>()
    var mDrawables = MapDrawable()
    private var princessImg: ImageView? = null
    var axeImg: ImageView? = null
    private var win: TextView? = null
    private var oneBlock = 0f
    private val n = 10
    var width = 0

    var direction = 1

    fun move(i: Boolean) {
        if (i)
            go(direction)
        else
            selectImage()
    }

    fun setPrincessImage(
        drawable: MapDrawable,
        ivPrincess: ImageView,
        ivAxe: ImageView,
        tvWin: TextView
    ) {
        this.princessImg = ivPrincess
        this.axeImg = ivAxe
        this.win = tvWin
        mDrawables = drawable
        itemCount.value = "0"
        isItem.value = "false"
    }

    fun setViewSize(width: Int) {
        this.width = width
        oneBlock = (width / n + width % n).toFloat()
        clear()
    }

    private fun selectImage() {
        when (direction) {
            0 -> {
                princessImg!!.setImageResource(R.drawable.princess_up)
            }
            1 -> {
                princessImg!!.setImageResource(R.drawable.princess_right)
            }
            2 -> {
                princessImg!!.setImageResource(R.drawable.princess_down)
            }
            3 -> {
                princessImg!!.setImageResource(R.drawable.princess_left)
            }
        }
    }

    private fun go(direction: Int) {
        val one = oneBlock
        when (direction) {
            //goint up
            0 -> {
                princessImg!!.y = (princessImg!!.y - one)
                axeImg!!.y = (axeImg!!.y - one)
            }
            //going right
            1 -> {
                princessImg!!.x = (princessImg!!.x + one)
                axeImg!!.x = (axeImg!!.x + one)
            }

            //going down
            2 -> {
                princessImg!!.y = (princessImg!!.y + one)
                axeImg!!.y = (axeImg!!.y + one)
            }

            //going left
            3 -> {
                princessImg!!.x = (princessImg!!.x - one)
                axeImg!!.x = (axeImg!!.x - one)
            }
        }
    }

    fun clear() {
        princessImg!!.x = oneBlock * mDrawables.princessX - oneBlock * 0.05f
        princessImg!!.y = oneBlock * mDrawables.princessY + oneBlock * 0.1f
        princessImg!!.setImageResource(R.drawable.princess_right)

        axeImg!!.x = oneBlock * mDrawables.princessX - oneBlock * 0.05f
        axeImg!!.y = oneBlock * mDrawables.princessY + oneBlock * 0.1f
        axeImg!!.setImageResource(R.drawable.ic_pick_axe)
        axeImg!!.isVisible = false
        direction = 1

        itemCount.value = "0"
        isItem.value = "false"
    }
}