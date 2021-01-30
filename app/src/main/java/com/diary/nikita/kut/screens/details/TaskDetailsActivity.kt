package com.diary.nikita.kut.screens.details

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.diary.nikita.kut.App
import com.diary.nikita.kut.R
import com.diary.nikita.kut.model.Task
import kotlinx.android.synthetic.main.activity_create_task.*
import kotlinx.android.synthetic.main.content_create_task.*

class TaskDetailsActivity : AppCompatActivity() {

    private var task: Task? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        toolbar.setTitle(R.string.create_task_activity_title)

        val intent = intent
        if (intent.hasExtra(EXTRA_TASK)) {
            val taskIntent: Task? = intent.getParcelableExtra(EXTRA_TASK)
            this.task = taskIntent
            text_view_task_title.setText(task?.title)
            text_view_task_description.setText(task?.description)
        } else task
        toolbar.title = if (task != null) "Edit task" else "Create task"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_note -> saveToDo()
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val EXTRA_TASK: String = "TaskDetailsActivity.EXTRA_TASK"
        fun start(caller: Activity, task: Task?) {
            val intent = Intent(caller, TaskDetailsActivity::class.java)
            if (task != null) {
                intent.putExtra(EXTRA_TASK, task)
            }
            caller.startActivity(intent)
        }
    }

//    private fun saveToDo() {
//        if (validateForms()) {
//            val id = if (task != null) task?.id else null
//            val toDo = Task(
//                id = id,
//                title = text_view_task_title.text.toString(),
//                description = text_view_task_description.text.toString(),
//                createdAt = System.currentTimeMillis(), done = false
//            )
//            val intent = Intent()
//            intent.putExtra(EXTRA_TASK, toDo)
//            setResult(Activity.RESULT_OK, intent)
//
//            if (intent.hasExtra(EXTRA_TASK)) {
//                App.instance?.taskDao?.update(task!!)
//            } else {
//                App.instance?.taskDao?.insertAll(task!!)
//            }
//            finish()
//        }
//    }

    private fun saveToDo() {
        if (validateForms()) {
            task?.title = text_view_task_title.toString()
            task?.description = text_view_task_description.toString()
            task?.done = false
            task?.createdAt = System.currentTimeMillis()

            if (intent.hasExtra(EXTRA_TASK)) {
                App.instance?.taskDao?.update(task!!)
            } else {
                App.instance?.taskDao?.insertAll(task!!)
            }
            finish()
        }
    }

    private fun validateForms(): Boolean {
        if (text_view_task_title.text.isEmpty()) {
            input_task_title.error = "Enter title"
            text_view_task_title.requestFocus()
            return false
        }
        if (text_view_task_description.text.isEmpty()) {
            input_task_description.error = "Enter description"
            text_view_task_description.requestFocus()
            return false
        }
        return true
    }

}