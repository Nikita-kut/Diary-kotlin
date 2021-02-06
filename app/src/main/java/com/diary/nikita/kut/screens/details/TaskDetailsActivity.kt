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
import com.diary.nikita.kut.utils.Constants
import com.diary.nikita.kut.utils.Constants.EXTRA_TASK
import com.diary.nikita.kut.utils.Constants.INTENT_TASK
import com.google.android.material.textfield.TextInputLayout

class TaskDetailsActivity : AppCompatActivity() {

    private var task: Task? =
        null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)
        setToolbar()

        val intent = intent
        if (intent != null && intent.hasExtra(INTENT_TASK)) {
            val task: Task? = intent.getParcelableExtra(INTENT_TASK)
            this.task = task
            getTitleView().setText(task?.title)
            getDescriptionView().setText(task?.description)
        }
        setToolbar().title = "Edit task"

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
            val id = if (task != null) task?.id else null
            val todo = Task(
                id = id,
                title = getTitleView().toString(),
                description = getDescriptionView().toString(),
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