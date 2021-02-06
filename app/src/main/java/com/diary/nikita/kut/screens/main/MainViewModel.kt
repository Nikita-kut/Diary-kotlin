package com.diary.nikita.kut.screens.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.diary.nikita.kut.data.DataBase
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

    fun getAllLiveData(): LiveData<List<Task>> {
        return repository.getAllLiveData()
    }
}