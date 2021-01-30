package com.diary.nikita.kut.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diary.nikita.kut.R
import com.diary.nikita.kut.screens.details.TaskDetailsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.app_name)

        recyclerView = recycler_view
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view.layoutManager = linearLayoutManager

        val adapter = Adapter()
        recyclerView?.adapter = adapter

        fab.setOnClickListener {
            TaskDetailsActivity.start(this, null)
        }

        val mainViewModel = ViewModelProviders.of(this).get<MainViewModel>(
            MainViewModel::class.java
        )
        mainViewModel.taskLiveData?.observe(
            this,
            Observer { tasks -> adapter.setItems(tasks) }
        )
    }

}