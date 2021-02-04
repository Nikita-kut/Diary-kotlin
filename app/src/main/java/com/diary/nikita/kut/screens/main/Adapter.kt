package com.diary.nikita.kut.screens.main

import android.app.Activity
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.diary.nikita.kut.R
import com.diary.nikita.kut.data.DataBase
import com.diary.nikita.kut.model.Task
import com.diary.nikita.kut.screens.details.TaskDetailsActivity

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private val sortedList: SortedList<Task>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sortedList[position])
    }

    override fun getItemCount(): Int = sortedList.size()

    fun setItems(task: List<Task>) {
        sortedList.replaceAll(task)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var taskTitle: TextView = itemView.findViewById(R.id.card_title)
        var taskDescription: TextView = itemView.findViewById(R.id.card_description)
        var task: Task? = null
        var completed: CheckBox = itemView.findViewById(R.id.completed)
        var silentUpdate = false
        var delete: View = itemView.findViewById(R.id.delete)

        init {
            itemView.setOnClickListener {
                TaskDetailsActivity.startWithoutTask(
                    itemView.context as Activity
                )
            }
            delete.setOnClickListener { DataBase.instance?.taskDao()?.delete(task!!) }
        }

        fun bind(task: Task) {
            this.task = task
            taskTitle.text = task.title
            taskDescription.text = task.description
            updateStrokeOut()
            silentUpdate = true
            completed.isChecked = task.done
            silentUpdate = false
        }

        private fun updateStrokeOut() {
            if (task!!.done) {
                taskTitle.paintFlags = taskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                taskDescription.paintFlags =
                    taskDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                taskTitle.paintFlags = taskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                taskDescription.paintFlags =
                    taskDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    init {
        sortedList = SortedList(
            Task::class.java,
            object : SortedList.Callback<Task>() {
                override fun compare(
                    o1: Task,
                    o2: Task
                ): Int {
                    if (!o2.done && o1.done) {
                        return 1
                    }
                    return if (o2.done && !o1.done) {
                        -1
                    } else (o2.createdAt - o1.createdAt).toInt()
                }

                override fun onChanged(position: Int, count: Int) {
                    notifyItemRangeChanged(position, count)
                }

                override fun areContentsTheSame(
                    oldItem: Task,
                    newItem: Task
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areItemsTheSame(
                    item1: Task,
                    item2: Task
                ): Boolean {
                    return item1.id == item2.id
                }

                override fun onInserted(position: Int, count: Int) {
                    notifyItemRangeInserted(position, count)
                }

                override fun onRemoved(position: Int, count: Int) {
                    notifyItemRangeRemoved(position, count)
                }

                override fun onMoved(fromPosition: Int, toPosition: Int) {
                    notifyItemMoved(fromPosition, toPosition)
                }
            })
    }

}

