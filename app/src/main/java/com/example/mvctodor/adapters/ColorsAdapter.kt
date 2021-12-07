package com.example.mvctodor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvctodor.databinding.ItemColorBinding

class ColorsAdapter(var list: List<Int>, var listener: OnItemClickListener) :
    RecyclerView.Adapter<ColorsAdapter.MyColorHolder>() {
    inner class MyColorHolder(var itemColorBinding: ItemColorBinding) :
        RecyclerView.ViewHolder(itemColorBinding.root) {
        fun onBind(color: Int) {
            itemView.setOnClickListener {
                listener.onItemClick(color)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyColorHolder {
        return MyColorHolder(
            ItemColorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyColorHolder, position: Int) {
        holder.onBind(list[position])
        holder.itemColorBinding.image.setBackgroundColor(list[position])
    }

    override fun getItemCount(): Int = list.size
    interface OnItemClickListener {
        fun onItemClick(color: Int)

    }
}