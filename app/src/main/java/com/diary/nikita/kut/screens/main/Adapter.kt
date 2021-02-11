package com.diary.nikita.kut.screens.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.diary.nikita.kut.R
import com.diary.nikita.kut.model.Task
import java.util.*

class Adapter(tdEvents: TdEvents) : RecyclerView.Adapter<Adapter.ViewHolder>(), Filterable {

    private var tasks: List<Task> = arrayListOf()
    private var sortedList: List<Task> = arrayListOf()
    private val listener: TdEvents = tdEvents

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sortedList[position], listener)
    }

    override fun getItemCount(): Int = sortedList.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(task: Task, listener: TdEvents) {

            itemView.findViewById<TextView>(R.id.card_title).text = task.title
            itemView.findViewById<TextView>(R.id.card_description).text = task.description
            itemView.findViewById<CheckBox>(R.id.completed).isChecked = task.done
            itemView.findViewById<ImageView>(R.id.delete).setOnClickListener {
                listener.onItemDeleted(task, adapterPosition)
            }
            itemView.setOnClickListener {
                listener.onViewClicked(task)
            }

        }
    }

    fun setAlltd(tasks: List<Task>) {
        this.tasks = tasks

        this.sortedList = tasks
        notifyDataSetChanged()
    }

    interface TdEvents {
        fun onItemDeleted(task: Task, position: Int)
        fun onViewClicked(task: Task)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                sortedList = if (charString.isEmpty()) {
                    tasks
                } else {
                    val sortedList = arrayListOf<Task>()
                    for (current in tasks) {
                        if ((current.title!!.toLowerCase(Locale.ROOT)
                                .contains(charString.toLowerCase(Locale.ROOT))) || current.description!!.toLowerCase(
                                Locale.ROOT
                            )
                                .contains(charString.toLowerCase(Locale.ROOT))
                        ) {
                            sortedList.add(current)
                        }
                    }
                    sortedList
                }
                val sortedResult = FilterResults()
                sortedResult.values = sortedList
                return sortedResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                sortedList = results?.values as List<Task>
                notifyDataSetChanged()
            }

        }
    }
}



