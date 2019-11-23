package com.mashup.friendlycoding.viewmodel

import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.activity.PlayActivity
import com.mashup.friendlycoding.model.MapDrawable
import com.mashup.friendlycoding.model.MapSettingModel
import com.mashup.friendlycoding.model.Stage
import kotlinx.android.synthetic.main.activity_play.*

class PrincessViewModel : ViewModel() {
    var itemCount = MutableLiveData<String>()
    var isItem = MutableLiveData<String>()
    var mDrawables = MapDrawable()
    lateinit var playActivity: PlayActivity
    private var princessImg: ImageView? = null
    private var win: TextView? = null
    private var oneBlock = 0f
    private val n = 10
    var width = 0

    var direction = 1

    fun move(i : Boolean) {
//        when (i) {
//            -1 -> clear()
//            0 -> {
//                go(0)
//                direction = 0
//            }// up
//            1 -> {
//                go(1)
//                direction = 1
//            }  // right
//            2 -> {
//                go(2)
//                direction = 2
//            }  // down
//            3 -> {
//                go(3)
//                direction = 3
//            }  // left
//            4 -> {
//                rotationLeft()
//            }
//            5 -> {
//                rotationRight()
//            }
 //       }
        if (i)
            go(direction)
        else
            selectImage()
    }

    fun setPrincessImage(drawable: MapDrawable, playActivity: PlayActivity) {
        this.playActivity = playActivity
        this.princessImg = playActivity.ivPrincess
        this.win = playActivity.tvWin
        mDrawables = drawable
        itemCount.value = "0"
        isItem.value = "false"
    }

    fun setViewSize(width: Int) {
        this.width = width
        oneBlock = (width / n + width % n).toFloat()
        //this.princessImg?.height ?: oneBlock.toInt()
        clear()
    }
//
//    private fun rotationLeft() {
//        direction -= 1
//        if (direction < 0)
//            direction += 4
//
//    }
//
//    private fun rotationRight() {
//        if (direction == 3)
//            direction = 0
//        else direction++
//
//        //selectDirection()
//    }

    fun selectImage() {
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
            }
            //going right
            1 -> {
                princessImg!!.x = (princessImg!!.x + one)
            }

            //going down
            2 -> {
                princessImg!!.y = (princessImg!!.y + one)
            }

            //going left
            3 -> {
                princessImg!!.x = (princessImg!!.x - one)
            }
        }
    }

    fun clear() {
        princessImg!!.x = oneBlock * mDrawables.princessX - oneBlock * 0.05f
        princessImg!!.y = oneBlock * mDrawables.princessY + oneBlock * 0.1f
        princessImg!!.setImageResource(R.drawable.princess_right)
        direction = 1
        //princessImg=tempPrincessImg
    }
}