package com.mashup.friendlycoding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.mashup.friendlycoding.databinding.ActivityMainBinding
import com.mashup.friendlycoding.viewmodel.PrincessViewModel


class MainActivity : AppCompatActivity() {

    private var mSienaTestViewModel = PrincessViewModel()
    lateinit var layoutMainView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        layoutMainView = this.findViewById(R.id.constraintLayout)
        binding.lifecycleOwner = this
        binding.testSiena = mSienaTestViewModel
        mSienaTestViewModel.setPrincessImage(binding.ivPrinsecess)


    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {

        Log.e("Layout Width - ", "Width" + (layoutMainView.width))

        mSienaTestViewModel.setViewSize(layoutMainView.width)

    }
}