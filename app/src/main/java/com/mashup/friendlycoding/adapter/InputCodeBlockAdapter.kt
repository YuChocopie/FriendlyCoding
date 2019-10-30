package com.mashup.friendlycoding.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.viewmodel.CodeBlock
import com.mashup.friendlycoding.viewmodel.CodeBlockViewModel
import kotlinx.android.synthetic.main.item_input_code_list.view.*

class InputCodeBlockAdapter(val mCodeBlockViewModel: CodeBlockViewModel, val adapter: CodeBlockAdapter) : RecyclerView.Adapter<InputCodeBlockAdapter.ViewHolder>() {
    var clickable = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        //보여줄 아이템 개수만큼 View를 생성
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_input_code_list, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount() = mCodeBlockViewModel.getBlockButton().size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO : getBlockButton이 아니라 stage 마다 달라지는 ArrayList<CodeBlock>으로 바꿀 것
        // TODO : 또한 이 어댑터가 BattleViewModel에서도 재활용되므로, mCodeBlockViewModel로 한정되지 말아야 함

        val item = mCodeBlockViewModel.getBlockButton()[position]

        val listener = View.OnClickListener {
            if (clickable) {
                mCodeBlockViewModel.addNewBlock(item)
                adapter.notifyDataSetChanged()
                Log.e("click", "Clicked")
            }
        }

        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        //ViewHolder 단위 객체로 View의 데이터를 설정
        private var view: View = v

        fun bind(listener: View.OnClickListener, item: CodeBlock) {
            view.input_code_name.text = item.funcName
            view.setOnClickListener(listener)
        }
    }
}