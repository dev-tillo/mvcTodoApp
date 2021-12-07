package com.example.mvctodor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvctodor.databinding.ItemCategoryBinding
import com.example.mvctodor.models.AppDatabase
import com.example.mvctodor.models.ListModel

class HomeAdapterList(
    private var list: List<ListModel>,
    context: Context,
    var listener: OnItemClickListener
) :
    RecyclerView.Adapter<HomeAdapterList.MyListHolder>() {
    private var appDatabase = AppDatabase.getInstance(context)

    inner class MyListHolder(var itemCategoryBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemCategoryBinding.root) {
        fun onBind(listModel: ListModel) {
            itemView.setOnClickListener {
                listener.onItemClick(listModel)
            }
            itemCategoryBinding.apply {
                layout.setBackgroundColor(listModel.color)
                name.text = listModel.name
                countTask.text= "${ appDatabase.serviceDao().getAllListName(listModel.name).size} Task"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListHolder {
        return MyListHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyListHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener {
        fun onItemClick(listModel: ListModel)

    }
}