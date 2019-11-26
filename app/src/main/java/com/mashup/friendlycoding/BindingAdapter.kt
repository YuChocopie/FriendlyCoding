package com.mashup.friendlycoding

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Build
import android.os.Handler
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mashup.friendlycoding.adapter.CodeBlockAdapter
import com.mashup.friendlycoding.adapter.InputCodeBlockAdapter
import com.mashup.friendlycoding.adapter.SelectActAdapter
import com.mashup.friendlycoding.model.CodeBlock
import com.mashup.friendlycoding.viewmodel.CodeBlockViewModel
import com.mashup.friendlycoding.viewmodel.MapSettingViewModel
import java.util.*

@BindingAdapter ("android:src")
fun ImageView.imgload (resId : Int) {
    this.setImageResource(resId)
}

// 위 아래로 움직이는 애니메이션 - 이는 attr/anim 으로 대체 가능 ?
@BindingAdapter("android:animation")
fun ImageView.moveObjects(maxHeight : Int) {
    val timer = Timer()
    val handler = Handler()
    val originalX = this.x
    val originalY = this.y
    val view = this
    var direction = true
    timer.schedule(object : TimerTask() {
        override fun run() {
            handler.post(object : Runnable {
                override
                fun run() {
                    if (view.y < originalY - maxHeight || view.y >= originalY)
                        direction = !direction

                    if (direction) {
                        view.animate().translationY(100f).duration = 2000
                    }
                    else
                        view.animate().translationY(-100f).duration = 2000
                }
            })
        }
    }, 0, 500)
}

@BindingAdapter("android:imageDrawable")
fun ImageView.bindDrawable(drawable: Drawable?) {
    this.setImageDrawable(drawable)
}

// 어댑터 연결 (세로)
@BindingAdapter("android:setVAdapter")
fun RecyclerView.bindVRC (adapter : RecyclerView.Adapter<CodeBlockAdapter.Holder>) {
    this.setHasFixedSize(true)
    this.layoutManager = LinearLayoutManager(this.context)
    this.adapter = adapter
}

// 어댑터 연결 (가로)
@BindingAdapter("android:setHAdapter")
fun RecyclerView.bindHRC (adapter : RecyclerView.Adapter<InputCodeBlockAdapter.Holder>) {
    this.setHasFixedSize(true)
    this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    this.adapter = adapter
}

// 스테이지 어댑터
@BindingAdapter("android:setSAdapter", "android:act")
fun RecyclerView.bindSRC (adapter: RecyclerView.Adapter<SelectActAdapter.Holder>, act : Int) {
    Log.e("act num", "$act")
    this.setHasFixedSize(true)
    this.layoutManager = LinearLayoutManager(this.context)
    this.adapter = adapter
    this.scrollToPosition(if (act == 5) 0 else 4 - act)
}

// 코드블록 삭제하는 롱클릭
@BindingAdapter("android:VM", "android:position")
fun View.bindClicker (mCodeBlockViewModel: CodeBlockViewModel, position : Int) {
    val listener = View.OnLongClickListener {
        mCodeBlockViewModel.deleteBlock(position)
        true
    }
    this.setOnLongClickListener(listener)
}

// for 문에다가 변수 심기
@BindingAdapter("android:argumentVM", "android:argument_position")
fun EditText.bindArgument (mCodeBlockViewModel: CodeBlockViewModel, position : Int) {
    val codeBlock = mCodeBlockViewModel.mRun.mCodeBlock.value!![position]
    this.addTextChangedListener(object : TextWatcher {
        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            try {
                codeBlock.argument = s.toString().toInt()
            } catch (e: Exception) {
            }
        }

        override fun afterTextChanged(arg0: Editable) {}
        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }
    })
}

// 코드에 컬러 입히기
@BindingAdapter("android:codeColor")
fun TextView.bindColor(codeBlock : CodeBlock) {
    val viewType = codeBlock.type
    var viewFuncName = codeBlock.funcName
    val codeBlue = resources.getString(R.string.codeBlue)

    when (viewType) {
        1 -> {//for
            val builder = SpannableStringBuilder(viewFuncName)
            var length = viewFuncName.length

            builder.setSpan(
                ForegroundColorSpan(Color.parseColor(codeBlue)),
                0,
                length - 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            this.text = builder
        }

        2 -> {//if
            val builder = SpannableStringBuilder(viewFuncName)
            var cnt = 0 //공백 카운트

            for (i in viewFuncName.indices) {
                if (viewFuncName[i] === ' ') {
                    cnt++
                }
            }
            builder.setSpan(
                ForegroundColorSpan(Color.parseColor(codeBlue)),
                cnt,
                cnt + 2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            this.text = builder
        }
        4 -> {//while
            val builder = SpannableStringBuilder(viewFuncName)
            var cnt = 0 //공백 카운트
            for (i in viewFuncName.indices) {
                if (viewFuncName[i] === ' ') {
                    cnt++
                }
            }
            builder.setSpan(
                ForegroundColorSpan(Color.parseColor(codeBlue)),
                cnt,
                cnt + 5,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            this.text = builder
        }
    }
}

@BindingAdapter("android:cloud")
fun ImageView.animateCloud(up : Float) {
    val ani = TranslateAnimation(0F, 0F, 0F, up)
    ani.duration = 2000
    ani.fillAfter = true
    ani.startOffset = 2000
    this.startAnimation(ani)
}

// 아이템 위치 옮기는 거 죄다 바인딩
@BindingAdapter("android:mapVM", "android:item_position")
fun ImageView.settingImg(vm : MapSettingViewModel, pos: Int) {
    if (vm.mDrawables.item.size <= pos) {
        this.isVisible = false
        return
    }
    else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setImageResource(vm.mDrawables.item[pos].resourceId)
        }
        this.isVisible = true
        this.x = vm.mDrawables.item[pos].Y.toFloat() * vm.oneBlock.value!!
        this.y = vm.mDrawables.item[pos].X.toFloat() * vm.oneBlock.value!!
    }
}

fun ignoreBlanks(code: String): String {
    var i = 0
    var start = 0
    while (code[i] == ' ') {
        start++
        i++
    }

    return code.substring(start)
}