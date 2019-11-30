package com.smu.friendlycoding.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.smu.friendlycoding.R
import com.smu.friendlycoding.adapter.SelectActAdapter
import com.smu.friendlycoding.model.StageItem

class SelectActViewModel(application: Application) : AndroidViewModel(application){
    var checkNum : Int = 30
    var check : Int = checkNum/10
    var scroll : Int = check / 10
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
        Log.e("backClick","this.list.size : ${this.list.size}, act: $act , this.check : ${this.check}")
        return true
    }

    fun goToStageSelector (act : Int) {
        actToStart.value = (this.list.size - act) * 10
    }

    fun getStageImage (act : Int) : StageItem {
        return this.list[act]
    }

    fun setLR (act : Int) : ImageView.ScaleType {
        if (act == 0) return ImageView.ScaleType.FIT_CENTER
        return if (act == this.list.size - 1 || act == this.list.size - 3) ImageView.ScaleType.FIT_START else ImageView.ScaleType.FIT_END
    }
}