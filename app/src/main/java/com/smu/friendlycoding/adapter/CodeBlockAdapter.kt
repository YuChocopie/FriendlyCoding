package com.smu.friendlycoding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smu.friendlycoding.databinding.ItemCodeBlockBinding
import com.smu.friendlycoding.viewmodel.CodeBlockViewModel

class CodeBlockAdapter(@LayoutRes val layoutID: Int, val mCodeBlockViewModel: CodeBlockViewModel) : RecyclerView.Adapter<CodeBlockAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemCodeBlockBinding>(layoutInflater, viewType, parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = if (mCodeBlockViewModel.mRun.mCodeBlock.value != null) mCodeBlockViewModel.mRun.mCodeBlock.value!!.size else 0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(mCodeBlockViewModel, position)
    }

    override fun getItemViewType(position: Int): Int = getLayoutIdForPosition()

    private fun getLayoutIdForPosition(): Int = layoutID

    inner class Holder(val binding: ItemCodeBlockBinding) :
        RecyclerView.ViewHolder(binding.itemView) {
        fun bind(mCodeBlockViewModel: CodeBlockViewModel, position: Int) {
            binding.codeBlockVM = mCodeBlockViewModel
            binding.position = position
            binding.executePendingBindings()
        }
    }
}