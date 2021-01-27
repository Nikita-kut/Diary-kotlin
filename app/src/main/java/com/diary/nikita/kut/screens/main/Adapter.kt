package com.diary.nikita.kut.screens.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diary.nikita.kut.R
import com.diary.nikita.kut.model.Task
import kotlinx.android.synthetic.main.task_item.view.*

class Adapter(toDo: ToDo) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    interface ToDo {
        fun onItemDelete(task: Task, position: Int)
        fun onViewClicked(task: Task)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task, listener: ToDo) {
            itemView.card_title.text = task.title
            itemView.card_description.text = task.description
            itemView.setOnClickListener {
                listener.onViewClicked(task)
            }
        }
    }

    private var taskList: List<Task> = listOf()
    private val listener: ToDo = toDo

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(taskList[position], listener)
    }


    fun setItems(task: List<Task>) {
        this.taskList = task
    }
}
