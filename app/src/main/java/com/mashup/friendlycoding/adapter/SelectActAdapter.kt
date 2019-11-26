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
    val items: ArrayList<StageItem>,
    val mapCheck: Int
) : RecyclerView.Adapter<SelectActAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_stage, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(items[position].item)
        holder.bg.setImageResource(items[position].backgroundImg)
        Log.e("positionmm", "$mapCheck")
        when (mapCheck) {
            0 -> {
                when (position) {
                    5 -> {
                        holder.image.setOnClickListener {
                            val intent = Intent(context, SelectStageActivity::class.java)
                            intent.putExtra("actNum", (6 - position) * 10)
                            context.startActivity(intent)
                        }
                    }
                }
            }
            1 -> {
                when (position) {
                    4, 5 -> {
                        holder.image.setOnClickListener {
                            val intent = Intent(context, SelectStageActivity::class.java)
                            intent.putExtra("actNum", (6 - position) * 10)
                            context.startActivity(intent)
                        }

                    }
                }

            }
            2 -> {
                when (position) {
                    3, 4, 5 -> {
                        holder.image.setOnClickListener {
                            val intent = Intent(context, SelectStageActivity::class.java)
                            intent.putExtra("actNum", (6 - position) * 10)
                            context.startActivity(intent)
                        }

                    }
                }
            }
            3 -> {
                when (position) {
                    2, 3, 4, 5 -> {
                        holder.image.setOnClickListener {
                            val intent = Intent(context, SelectStageActivity::class.java)
                            intent.putExtra("actNum", (6 - position) * 10)
                            context.startActivity(intent)
                        }

                    }
                }
            }

            4 -> {
                when (position) {
                    1, 2, 3, 4, 5 -> {
                        holder.image.setOnClickListener {
                            val intent = Intent(context, SelectStageActivity::class.java)
                            intent.putExtra("actNum", (6 - position) * 10)
                            context.startActivity(intent)
                        }

                    }
                }
            }
//            5 -> {
//                when (position) {
//                    0, 1, 2, 3, 4, 5 -> {
//                        holder.image.setOnClickListener {
//                            val intent = Intent(context, SelectStageActivity::class.java)
//                            intent.putExtra("actNum", (6 - position) * 10)
//                            context.startActivity(intent)
//                        }
//
//                    }
//                }
//
//            }
        }


        var check = position % 5
        if (check == 1 || check == 4) {
            holder.image.scaleType = ImageView.ScaleType.FIT_END
        } else if (check == 0 || check == 2 || check == 3 || check == 5) {
            holder.image.scaleType = ImageView.ScaleType.FIT_START
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