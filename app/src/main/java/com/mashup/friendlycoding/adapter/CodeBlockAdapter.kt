package com.mashup.friendlycoding.adapter

import android.app.PendingIntent.getActivity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.repository.CodeBlock
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import com.mashup.friendlycoding.databinding.ActivityMainBinding
import com.mashup.friendlycoding.databinding.CodeBlockListBinding
import com.mashup.friendlycoding.viewmodel.CodeBlockViewModel
import kotlinx.android.synthetic.main.code_block_list.view.*

class CodeBlockAdapter(val mContext: Context, val CodeBlocks: ArrayList<CodeBlock>) :
    RecyclerView.Adapter<CodeBlockAdapter.Holder>() {
    private val mCodeBlockViewModel = CodeBlockViewModel()
    lateinit var binding: CodeBlockListBinding


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): Holder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.code_block_list, viewGroup, false)



        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // Set the name of the 'CodeBlock'
        //binding.position = position
        //holder.bind(CodeBlocks[position], mContext)
        //생성된 View에 보여줄 데이터를 설정

        val item = CodeBlocks[position]
        //길게 눌렀을 때

        val listener = View.OnLongClickListener {
            Toast.makeText(it.context, "${item.funcName}가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            CodeBlocks.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, CodeBlocks.size)
            true
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView

        fun bind(listener: View.OnLongClickListener, codeBlock: CodeBlock) {
            //binding.executePendingBindings()
            view.func_name.text = codeBlock.funcName
            //setColor(view)
            view.setOnLongClickListener(listener)
        }
        private fun setColor(view: View) {
            when (view.func_name.text) {
                "move" -> view.setBackgroundColor(Color.RED)
                "turnLeft" -> view.setBackgroundColor(Color.GREEN)
                "turnRight" -> view.setBackgroundColor(Color.BLUE)
                "Pick" -> view.setBackgroundColor(Color.MAGENTA)
                "Eat" -> view.setBackgroundColor(Color.YELLOW)
                "Something" -> view.setBackgroundColor(Color.CYAN)
                else -> {
                    view.setBackgroundColor(Color.GRAY)
                }


            }
            //view.setBackgroundColor(Color.rgb(178, 204, 255))
        }
    }

    override fun getItemCount(): Int {
        return CodeBlocks.size
    }
}