package com.diary.nikita.kut.screens.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diary.nikita.kut.R
import com.diary.nikita.kut.model.Task
import com.diary.nikita.kut.screens.details.TaskDetailsActivity
import com.diary.nikita.kut.utils.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), Adapter.TdEvents {

    private var adapter: Adapter = Adapter(this)
    private lateinit var taskViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setToolbar()
        setRecyclerView()
        setViewModel()

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, TaskDetailsActivity::class.java)
            startActivityForResult(intent, Constants.EXTRA_TASK)
        }

        val deleteTask = findViewById<ImageView>(R.id.delete)
        deleteTask.setOnClickListener {
            taskViewModel.deleteTask()
        }
    }

    private fun setToolbar() {
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbarMain)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
    }

    private fun setRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    private fun setViewModel() {
        taskViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        taskViewModel.getActiveTasks().observe(this, Observer { adapter.setAlltd(it) })
    }

    override fun onItemDeleted(task: Task, position: Int) {
        taskViewModel.deleteTask(task)
        adapter.notifyItemRemoved(position)
    }

    override fun onViewClicked(task: Task) {
        val intent = Intent(this, TaskDetailsActivity::class.java)
        intent.putExtra(Constants.INTENT_TASK, task)
        startActivityForResult(intent, Constants.EXTRA_MODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val todo = data?.getParcelableExtra<Task>(Constants.INTENT_TASK)!!
            when (requestCode) {
                Constants.EXTRA_TASK -> {
                    taskViewModel.saveTask(todo)
                }
                Constants.EXTRA_MODE -> {
                    taskViewModel.updateTask(todo)
                }
            }
        }
    }

}