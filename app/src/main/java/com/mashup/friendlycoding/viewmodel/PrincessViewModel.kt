package com.mashup.friendlycoding.viewmodel

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.friendlycoding.R

class PrincessViewModel : ViewModel() {
    var metBoss = MutableLiveData<Boolean>()

    private var princessImg: ImageView? = null
    private var win: TextView? = null
    private var oneBlock = 0f
    private val n = 10
    var width = 0


    private var direction = 0

    private var princessContext: Context? = null

    fun move(i: Int) {
        when (i) {
            -1 -> clear()
            0 -> {
                go(0)
                direction = 0
            }// up
            1 -> {
                go(1)
                direction = 1
            }  // right
            2 -> {
                go(2)
                direction = 2
            }  // down
            3 -> {
                go(3)
                direction = 3
            }  // left
            4 -> {
                rotationLeft()
            }
            5 -> {
                rotationRight()
            }
        }
    }

    fun setPrincessImage(view: ImageView, win: TextView, princessContext: Context) {
//        if (princessImg == null) {
//            imgX = view.rotationX
//            imgY = view.rotationY
//            Log.e("setPrincessImage","호출됨")
//        }
        this.princessImg = view
        this.win = win
        this.princessContext = princessContext
        metBoss.value = false

//        if (tempPrincessImg == null) {
//            this.tempPrincessImg = view //초기화용 공주 이미지
//        }

    }

    fun setViewSize(width: Int) {
        this.width = width
        oneBlock = (width / n + width % n).toFloat()
        //this.princessImg?.height ?: oneBlock.toInt()
        clear()
    }

    private fun rotationLeft() {
        // TODO : 공주 사진 변경
//        val animation =
//            AnimationUtils.loadAnimation(princessContext, R.anim.rotate_left)
        //princessImg!!.startAnimation(animation)

        //selectDirection()
        direction -= 1
        if (direction < 0)
            direction += 4
        selectImage()

    }

    private fun rotationRight() {
        // TODO : 공주 사진 변경
//        val animation =
//            AnimationUtils.loadAnimation(princessContext, R.anim.rotate_right)
        //princessImg!!.startAnimation(animation)
        //selectDirection()
        if (direction == 3)
            direction = 0
        else direction++

        //selectDirection()
        selectImage()
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

    private fun selectDirection() {
        //코드로 애니메이션 적용
        var rotateAnim: RotateAnimation? = null
        when (direction) {
            0 -> {
                rotateAnim = RotateAnimation(
                    0.0f,
                    90.0f,
                    Animation.RELATIVE_TO_SELF,
                    0.5F,
                    Animation.RELATIVE_TO_SELF,
                    0.5F
                )
                Log.e("rotation", "$direction")
            }
            1 -> {
                rotateAnim = RotateAnimation(
                    90.0f,
                    180.0f,
                    Animation.RELATIVE_TO_SELF,
                    0.5F,
                    Animation.RELATIVE_TO_SELF,
                    0.5F
                )
                Log.e("rotation", "$direction")
            }
            3 -> {
                rotateAnim = RotateAnimation(
                    180.0f,
                    270.0f,
                    Animation.RELATIVE_TO_SELF,
                    0.5F,
                    Animation.RELATIVE_TO_SELF,
                    0.5F
                )
                Log.e("rotation", "$direction")
            }
            4 -> {
                rotateAnim = RotateAnimation(
                    270.0f,
                    360.0f,
                    Animation.RELATIVE_TO_SELF,
                    0.5F,
                    Animation.RELATIVE_TO_SELF,
                    0.5F
                )
                Log.e("rotation", "$direction")
            }

        }



        rotateAnim?.duration = 300
        rotateAnim?.fillAfter = true

        if (rotateAnim != null) {
            princessImg!!.startAnimation(rotateAnim)
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
//        princessImg!!.rotationX=imgX
//        princessImg!!.rotationY=imgY
        princessImg!!.x = oneBlock * 0 - oneBlock * 0.05f
        princessImg!!.y = oneBlock * 9 + oneBlock * 0.1f
        princessImg!!.setImageResource(R.drawable.princess_right)
        direction = 1
        //princessImg=tempPrincessImg
    }
}