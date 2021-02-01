package com.diary.nikita.kut.screens.details

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.diary.nikita.kut.R
import com.diary.nikita.kut.data.DataBase
import com.diary.nikita.kut.model.Task
import com.google.android.material.textfield.TextInputLayout

class TaskDetailsActivity : AppCompatActivity() {

    lateinit var task: Task

    companion object {
        private val EXTRA_TASK: String = "TaskDetailsActivity.EXTRA_TASK"
        private val EXTRA_MODE: String = "TaskDetailsActivity.EXTRA_MODE"
        @JvmStatic
        fun startWithTask(caller: Activity, task: Task) {
            val intent = Intent(caller, TaskDetailsActivity::class.java)
            intent.putExtra(EXTRA_TASK, task)
            intent.putExtra(EXTRA_MODE, true)
            caller.startActivity(intent)
        }
        @JvmStatic
        fun startWithoutTask(caller: Activity) {
            val intent = Intent(caller, TaskDetailsActivity::class.java)
            intent.putExtra(EXTRA_MODE, false)
            caller.startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)
        setToolbar()

        task = intent.getParcelableExtra(EXTRA_TASK) ?: throw RuntimeException("")

        if (intent != null && intent.hasExtra(EXTRA_TASK)) {
            getTitleView().setText(task.title)
            getDescriptionView().setText(task.description)
        }
        setToolbar().title = "Edit task"

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

    private fun setToolbar(): Toolbar {
        val toolbar: Toolbar = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        toolbar.setTitle(R.string.create_task_activity_title)
        return toolbar
    }

    private fun saveToDo() {
        if (validateForms()) {
            task.title = getTitleView().toString()
            task.description = getDescriptionView().toString()
            task.done = false
            task.createdAt = System.currentTimeMillis()

            if (intent.hasExtra(EXTRA_TASK)) {
                DataBase.getInstance(this)?.taskDao()?.update(task)
            } else {
                DataBase.getInstance(this)?.taskDao()?.insertAll(task)
            }
            finish()
        }
    }

    private fun validateForms(): Boolean {
        val tvInputTitle: TextInputLayout = findViewById(R.id.input_task_title)
        val tvInputDescription: TextInputLayout = findViewById(R.id.input_task_description)
        if (getTitleView().text.isEmpty()) {
            tvInputTitle.error = "Enter title"
            getTitleView().requestFocus()
            return false
        }
        if (getDescriptionView().text.isEmpty()) {
            tvInputDescription.error = "Enter description"
            getDescriptionView().requestFocus()
            return false
        }
        return true
    }

    private fun getTitleView(): EditText = findViewById(R.id.text_view_task_title)
    private fun getDescriptionView(): EditText = findViewById(R.id.text_view_task_description)


}