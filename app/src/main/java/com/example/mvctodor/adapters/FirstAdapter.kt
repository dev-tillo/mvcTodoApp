package com.example.mvctodor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvctodor.R
import com.example.mvctodor.databinding.ItemTaskBinding
import com.example.mvctodor.models.AppDatabase
import com.example.mvctodor.models.TaskModel

class FirstAdapter(var list: List<TaskModel>, var context: Context) :
    RecyclerView.Adapter<FirstAdapter.MyFirstTaskHolder>() {

    private var appDatabase: AppDatabase = AppDatabase.getInstance(context)
    private var counter = 0


    inner class MyFirstTaskHolder(var itemTaskBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(itemTaskBinding.root) {
        fun onBind(taskModel: TaskModel) {
            itemTaskBinding.apply {
                if (taskModel.isHave) {
                    imageCheck.setImageResource(R.drawable.ic_marked)
                    calendar.visibility = View.VISIBLE
                    time.visibility = View.VISIBLE
                } else {
                    imageCheck.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24)
                    calendar.visibility = View.INVISIBLE
                    time.visibility = View.INVISIBLE
                }

                tvName.apply {
                    isSingleLine = true
                    isSelected = true
                }

                imageCheck.setOnClickListener {
                    when (counter) {
                        0 -> {
                            taskModel.isHave = true
                            counter++
                            calendar.visibility = View.VISIBLE
                            time.visibility = View.VISIBLE
                            imageCheck.setImageResource(R.drawable.ic_marked)
                            appDatabase.serviceDao().editTask(taskModel)
                        }
                        1 -> {
                            taskModel.isHave = false
                            counter--
                            calendar.visibility = View.INVISIBLE
                            time.visibility = View.INVISIBLE
                            appDatabase.serviceDao().editTask(taskModel)
                            imageCheck.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24)
                        }
                    }
                }

                calendar.text = taskModel.date
                time.text = taskModel.time
                tvName.text = taskModel.description
                imageColor.setBackgroundColor(taskModel.listColor)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFirstTaskHolder {
        return MyFirstTaskHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyFirstTaskHolder, position: Int) {
        holder.onBind(list[position])

    }

    override fun getItemCount(): Int = list.size

}