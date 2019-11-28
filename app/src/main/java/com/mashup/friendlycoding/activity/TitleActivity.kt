package com.mashup.friendlycoding.activity

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.databinding.ActivityTitleBinding
import com.mashup.friendlycoding.viewmodel.TitleViewModel
import kotlinx.android.synthetic.main.activity_title.*

class TitleActivity : BaseActivity() {
    val mTitleViewModel = TitleViewModel()
    lateinit var mp : MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityTitleBinding>(this, R.layout.activity_title)
        binding.lifecycleOwner = this
        binding.titleVM = this.mTitleViewModel

        this.mTitleViewModel.goToActivity.observe(this, Observer {
            when (it) {
                0 -> { // 게임 실행
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

                1 -> { // 음소거
                    super.isMute = !super.isMute
                    if (super.isMute) {
                        sound.setImageResource(R.drawable.mute)
                        mp.pause()
                    }

                    else {
                        sound.setImageResource(R.drawable.sound)
                        mp.start()
                    }
                }

                2 -> { // 만든 사람들
                    val intent = Intent(this, CreditActivity::class.java)
                    startActivity(intent)
                }
            }
        })

    }

    override fun onStart() {
        super.onStart()
        this.mp = MediaPlayer.create(this, R.raw.title_song)
        this.mp.isLooping = true
    }

    override fun onResume() {
        super.onResume()
        if (!super.isMute) this.mp.start()
    }

    override fun onPause() {
        super.onPause()
        this.mp.stop()
    }

}
