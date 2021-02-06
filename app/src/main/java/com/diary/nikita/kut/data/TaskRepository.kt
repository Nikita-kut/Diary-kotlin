package com.diary.nikita.kut.data

import android.app.Application
import android.util.Log.d
import androidx.lifecycle.LiveData
import com.diary.nikita.kut.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TaskRepository(application: Application) {

    private val TAG = "TaskRepository"
    private val taskDao: TaskDao
    private val allLiveData: LiveData<List<Task>>


    init {
        val dataBase = DataBase.getInstance(application.applicationContext)
        taskDao = dataBase!!.taskDao()
        allLiveData = taskDao.getAllLiveData()
    }

    fun getAllLiveData(): LiveData<List<Task>> {
        d(TAG, ":getAllLiveData()")
        return allLiveData
    }

    fun saveTask(task: Task) = runBlocking {
        d(TAG, ":saveTask()")
        this.launch(Dispatchers.IO) { taskDao.saveTask(task) }
    }

    fun updateTask(task: Task) = runBlocking {
        d(TAG, ":updateTask()")
        this.launch(Dispatchers.IO) { taskDao.update(task) }
    }

    fun deleteTask(task: Task) = runBlocking {
        d(TAG, ":deleteTask()")
        this.launch(Dispatchers.IO) { taskDao.delete(task) }
    }
}