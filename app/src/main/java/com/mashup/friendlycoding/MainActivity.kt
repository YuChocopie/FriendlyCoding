package com.mashup.friendlycoding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.friendlycoding.adapter.CodeBlockAdapter
import com.mashup.friendlycoding.databinding.ActivityMainBinding
import com.mashup.friendlycoding.repository.CodeBlock
import com.mashup.friendlycoding.viewmodel.CodeBlockViewModel
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.databinding.Observable


class MainActivity : AppCompatActivity() {
    private val mCodeBlockViewModel = CodeBlockViewModel()
    lateinit var mAdapter : CodeBlockAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.main = this
        binding.lifecycleOwner = this
        binding.codeBlockVM = mCodeBlockViewModel
        binding.codeBlock = mCodeBlockViewModel.getBlockButton()

        //initialization
        mCodeBlockViewModel.init()

        //recycler view connects
        mAdapter = CodeBlockAdapter(this, mCodeBlockViewModel.getCodeBlock()?.value!!)
        var linearLayoutManager = LinearLayoutManager(this)
        rc_code_block_list.layoutManager = linearLayoutManager
        rc_code_block_list.adapter = mAdapter

        mCodeBlockViewModel.getCodeBlock()?.observe(this, object : Observer<List<CodeBlock>> {
            override  fun onChanged(@Nullable codeBlock: List<CodeBlock>) {
                mAdapter.notifyDataSetChanged()
            }
        })
    }
}