package com.mashup.friendlycoding

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.friendlycoding.adapter.CodeBlockAdapter
import com.mashup.friendlycoding.databinding.ActivityMainBinding
import com.mashup.friendlycoding.repository.CodeBlock
import com.mashup.friendlycoding.viewmodel.CodeBlockViewModel
import com.mashup.friendlycoding.viewmodel.PrincessViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var mPrincessViewModel = PrincessViewModel()
    lateinit var layoutMainView: View
    private val mCodeBlockViewModel = CodeBlockViewModel()
    private val mRun = mCodeBlockViewModel.getRunModel()

    lateinit var mAdapter: CodeBlockAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        layoutMainView = this.findViewById(R.id.constraintLayout)
        binding.lifecycleOwner = this
        binding.princessVM = mPrincessViewModel
        mPrincessViewModel.setPrincessImage(binding.ivPrinsecess)


        // bind Code Block View Model
        binding.codeBlockVM = mCodeBlockViewModel
        binding.codeBlock = mCodeBlockViewModel.getBlockButton()

        mCodeBlockViewModel.init()

        //recycler view connects
        mAdapter = CodeBlockAdapter(this, mCodeBlockViewModel.getCodeBlock().value!!)
        val linearLayoutManager = LinearLayoutManager(this)
        rc_code_block_list.layoutManager = linearLayoutManager
        rc_code_block_list.adapter = mAdapter

        mCodeBlockViewModel.getCodeBlock().observe(this,
            Observer<List<CodeBlock>> { mAdapter.notifyDataSetChanged() })

        mRun.getMoving().observe(this, Observer<Int> { t ->
            when (t) {
                0 -> {
                    Thread.sleep(1000)
                    mPrincessViewModel.go()
                }

                1 -> {
                    mPrincessViewModel.rotationLeft()
                }
                2 -> {
                    Thread.sleep(1000)
                    mPrincessViewModel.rotationRigjt()
                }
            }
        })
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        Log.e("Layout Width - ", "Width" + (layoutMainView.width))
        mPrincessViewModel.setViewSize(layoutMainView.width)

    }
}