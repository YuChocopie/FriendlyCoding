package com.mashup.friendlycoding.viewmodel

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.adapter.SelectActAdapter
import com.mashup.friendlycoding.model.StageItem

class SelectActViewModel(application: Application) : AndroidViewModel(application){
    var checkNum : Int = 1
    var check : Int = checkNum/10
    lateinit var adapter : SelectActAdapter
    val actToStart = MutableLiveData<Int>()

    val list = arrayListOf(
        StageItem(R.drawable.bg_stage_map_05, R.drawable.bg_stage_select_05),
        StageItem(R.drawable.bg_stage_map_04, R.drawable.bg_stage_select_04),
        StageItem(R.drawable.bg_stage_map_03, R.drawable.bg_stage_select_03),
        StageItem(R.drawable.bg_stage_map_02, R.drawable.bg_stage_select_02),
        StageItem(R.drawable.bg_stage_map_01, R.drawable.bg_stage_select_01)
    )

    fun init() {
        if (this.check >= 3) {
            list.add(0, StageItem(R.drawable.bg_stage_map_01, R.drawable.bg_stage_select_01)) // 액트 4의 배경과 버튼
        }
        if (this.check >= 4) {
            list.add(0, StageItem(R.drawable.bg_stage_map_02, R.drawable.bg_stage_select_02)) // 액트 5의 배경과 버튼
        }
        this.adapter = SelectActAdapter(R.layout.item_stage, this)
        actToStart.value = -1
    }

    fun getSelectActAdapter() : SelectActAdapter{
        return this.adapter
    }

    fun cloudVisibility(act : Int) : Int {
        return if (act > this.check) View.VISIBLE else View.GONE
    }

    fun banClick (act : Int) : Boolean {
        return (this.list.size - act) - 1 <= this.check
    }

    fun goToStageSelector (act : Int) {
        actToStart.value = (this.list.size - act) * 10
    }

    fun getStageImage (act : Int) : StageItem {
        return this.list[act]
    }

    fun setLR (act : Int) : ImageView.ScaleType {
        if (this.check >= 4 && act == 0) return ImageView.ScaleType.CENTER
        return if (act == this.list.size - 1 || act == this.list.size - 3) ImageView.ScaleType.FIT_START else ImageView.ScaleType.FIT_END
    }
}