package com.mashup.friendlycoding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mashup.friendlycoding.BR
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.databinding.ItemCodeBlockBinding
import com.mashup.friendlycoding.databinding.ItemInputCodeBinding
import com.mashup.friendlycoding.model.CodeBlock
import com.mashup.friendlycoding.viewmodel.CodeBlockViewModel
import com.mashup.friendlycoding.viewmodel.MapSettingViewModel
import kotlinx.android.synthetic.main.item_input_code.view.*

class InputCodeBlockAdapter(@LayoutRes val layoutID: Int, val mCodeBlockViewModel: CodeBlockViewModel) :
    RecyclerView.Adapter<InputCodeBlockAdapter.Holder>() {
    lateinit var offeredBlock: ArrayList<CodeBlock>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemInputCodeBinding>(layoutInflater, viewType, parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = this.offeredBlock.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    override fun getItemViewType(position: Int): Int = getLayoutIdForPosition()

    private fun getLayoutIdForPosition(): Int = layoutID

    fun setAddingBlock(addingBlock: ArrayList<CodeBlock>) {
        this.offeredBlock = addingBlock
    }

    inner class Holder(val binding: ItemInputCodeBinding) :
        RecyclerView.ViewHolder(binding.itemView) {
        fun bind(position: Int) {
            binding.codeBlockVM = mCodeBlockViewModel
            binding.position = position
            binding.adapter = this@InputCodeBlockAdapter
            binding.executePendingBindings()
        }
    }

    fun getCodeBlock(position: Int): CodeBlock = this.offeredBlock[position]
}