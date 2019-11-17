package com.mashup.friendlycoding.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.mashup.friendlycoding.R
import kotlinx.android.synthetic.main.activity_select_stage.*

class SelectStageActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_stage)

        val actNum = intent.getIntExtra("actNum", 1)

        tvActTitle.text = "액트 " + (actNum/10).toString()

        val intent = Intent(this, PlayActivity::class.java)
        val listener = View.OnClickListener{
            when (it.id) {
                R.id.ivActStage1 -> intent.putExtra("stageNum", actNum+1)
                R.id.ivActStage2 -> intent.putExtra("stageNum", actNum+2)
                R.id.ivActStage3 -> intent.putExtra("stageNum", actNum+3)
            }
            startActivity(intent)
        }
        ivActStage1.setOnClickListener(listener)
        ivActStage2.setOnClickListener(listener)
        ivActStage3.setOnClickListener(listener)
    }
}