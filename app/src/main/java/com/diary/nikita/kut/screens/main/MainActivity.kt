package com.diary.nikita.kut.screens.main

import android.content.Intent
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
import com.diary.nikita.kut.utils.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), Adapter.TdEvents {

    private var adapter: Adapter = Adapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        setRecyclerView(recyclerView)

        val taskViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        taskViewModel.getAllLiveData().observe(this, Observer { adapter.setAlltd(it) })

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, TaskDetailsActivity::class.java)
            startActivityForResult(intent, Constants.EXTRA_TASK)
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

    private fun setRecyclerView(recyclerView: RecyclerView) {
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        adapter = Adapter(this)
        recyclerView.adapter = adapter
    }


    private fun setToolbar() {
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbarMain)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
    }

    override fun onItemDeleted(note: Task, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onViewClicked(note: Task) {
        TODO("Not yet implemented")
    }

}