package com.mashup.friendlycoding.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mashup.friendlycoding.R
import com.mashup.friendlycoding.activity.SelectStageActivity
import com.mashup.friendlycoding.model.StageItem
import kotlinx.android.synthetic.main.item_stage.view.*

class SelectActAdapter(
    val context: Context,
    val items: ArrayList<StageItem>
) : RecyclerView.Adapter<SelectActAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_stage, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(items[position].item)
        holder.bg.setImageResource(items[position].backgroundImg)
        holder.image.setOnClickListener {
            if (position == 0) {
                Log.d("TAG", "${position}번째")
            } else if (position == 1) {
                Log.d("TAG", "${position}번째")
            } else if (position == 2) {
                Log.d("TAG", "${position}번째")
            } else if (position == 3) {
                Log.d("TAG", "${position}번째")
            } else if (position == 4) {
                Log.d("TAG", "${position}번째")
            }

            val intent = Intent(context, SelectStageActivity::class.java)
            intent.putExtra("actNum", (6 - position)*10)
            context.startActivity(intent)
        }

        var check = position % 5
        if (check == 1|| check == 4) {
            holder.image.scaleType= ImageView.ScaleType.FIT_END
        } else if (check == 0 || check == 2|| check == 3|| check == 5) {
            holder.image.scaleType= ImageView.ScaleType.FIT_START
        }
//        else if (check == 0 || check == 4) {
//            holder.apply {
//                bind(items[position], context)
//                itemView.tag = items
//                itemView.ivStageImage.setPadding(50, 0, 0, 0)
//            }
//        }
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById<ImageView>(R.id.ivStageImage)
        val bg: ImageView = itemView.findViewById<ImageView>(R.id.ivStageBackgroundImage)
        fun bind(list: StageItem, context: Context) {
            image?.setImageResource(R.drawable.cave)
        }
    }
}