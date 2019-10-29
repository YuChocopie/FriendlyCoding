package com.mashup.friendlycoding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.databinding.StageListBinding
import com.mashup.friendlycoding.viewmodel.StageViewModel
import kotlinx.android.synthetic.main.stage_list.view.*

class StageAdapter(private val items: ArrayList<StageViewModel>) :
    RecyclerView.Adapter<StageAdapter.ViewHolder>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        var check = position % 5

        /*
        *
        * 001
        * 010
        * 100
        * 010
        * 001
        *
        * */
        //임시 width 추가
        if (check == 2) {
            holder.apply {
                bind(item)
                itemView.tag = item
                itemView.stageImage.setPadding(0, 0, 0, 0)
            }
        } else if (check == 1 || check == 3) {
            holder.apply {
                bind(item)
                itemView.tag = item
                itemView.stageImage.setPadding(
                    380, 0, 0, 0
                )
            }
        } else if (check == 0 || check == 4) {
            holder.apply {
                bind(item)
                itemView.tag = item
                itemView.stageImage.setPadding(760, 0, 0, 0)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.stage_list,
                parent,
                false
            )
        )
    }

    class ViewHolder(private val binding: StageListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StageViewModel) {
            binding.apply {
                stageVM = item
            }
        }
    }
}