package com.diary.nikita.kut.screens.main

import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diary.nikita.kut.R
import com.diary.nikita.kut.data.DataBase
import com.diary.nikita.kut.data.TaskRepository
import com.diary.nikita.kut.model.Task
import com.diary.nikita.kut.screens.details.TaskDetailsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbarMain)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        setSupportActionBar(toolbar)

        setRecyclerView(recyclerView)

        setViewModel()

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            TaskDetailsActivity.startWithoutTask(this)
        }

        val calendarView: CalendarView = findViewById(R.id.calendar_view)
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Toast.makeText(
                this, """
                Year $year
                Month ${month + 1}
                Day $dayOfMonth
            """, Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun onTaskClicked(task: Task) {
        TaskDetailsActivity.startWithTask(this, task)
    }

    private fun setRecyclerView(recyclerView: RecyclerView) {
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager

        recyclerView.adapter = adapter
    }

    private fun setViewModel() {
        val mainViewModel = ViewModelProviders.of(this).get<MainViewModel>(
            MainViewModel::class.java
        )
        mainViewModel.taskLiveData?.observe(
            this,
            Observer { tasks -> adapter.setItems(tasks) }
        )
    }

}