package com.diary.nikita.kut.screens.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.diary.nikita.kut.data.TaskRepository
import com.diary.nikita.kut.model.Task

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository = TaskRepository(application)

    fun saveTask(task: Task) {
        repository.saveTask(task)
    }

    fun updateTask(task: Task) {
        repository.updateTask(task)
    }

    fun deleteTask(task: Task) {
        repository.deleteTask(task)
    }

    fun getActiveTasks(): LiveData<List<Task>> {
        return repository.getActiveTasks()
    }
}