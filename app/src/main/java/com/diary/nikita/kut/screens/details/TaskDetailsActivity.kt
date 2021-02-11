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
import com.diary.nikita.kut.model.Task
import com.diary.nikita.kut.utils.Constants.INTENT_TASK
import com.google.android.material.textfield.TextInputLayout

class TaskDetailsActivity : AppCompatActivity() {

    private var task: Task? =
        null
    private val toolbar: Toolbar by lazy { findViewById<Toolbar>(R.id.toolbar_activity_create_task) }
    private val etTitle: EditText by lazy { findViewById<EditText>(R.id.edit_text_task_title) }
    private val etDescription: EditText by lazy { findViewById<EditText>(R.id.edit_text_task_description) }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        toolbar.setTitle(R.string.create_task_activity_title)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val intent = intent
        if (intent != null && intent.hasExtra(INTENT_TASK)) {
            val task: Task? = intent.getParcelableExtra(INTENT_TASK)
            this.task = task
            etTitle.setText(task?.title)
            etDescription.setText(task?.description)
            etTitle.setSelection(etTitle.text.length)
        }
        toolbar.title = if (task != null) "Edit task" else "Create task"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_note -> saveToDo()
            android.R.id.home -> finish()
        }
        return true
    }


    private fun saveToDo() {
        if (validateForms()) {
            val id = if (task != null) task?.id else null
            val todo = Task(
                id = id,
                title = etTitle.text.toString(),
                description = etDescription.text.toString(),
                createdAt = System.currentTimeMillis(),
                done = false
            )

            val intent = Intent()
            intent.putExtra(INTENT_TASK, todo)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun validateForms(): Boolean {
        val tvInputTitle: TextInputLayout = findViewById(R.id.input_task_title)
        val tvInputDescription: TextInputLayout = findViewById(R.id.input_task_description)
        if (etTitle.text.isEmpty()) {
            tvInputTitle.error = "Enter title"
            etTitle.requestFocus()
            return false
        }
        if (etDescription.text.isEmpty()) {
            tvInputDescription.error = "Enter description"
            etDescription.requestFocus()
            return false
        }
        return true
    }


}