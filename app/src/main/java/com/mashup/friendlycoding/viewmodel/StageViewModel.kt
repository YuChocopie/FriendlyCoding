package com.mashup.friendlycoding.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel

class StageViewModel : ViewModel(){
    var check : Int = 1

    fun cloudVisibility(act : Int) : Int {
        return if (act > this.check) View.VISIBLE else View.GONE
    }
}