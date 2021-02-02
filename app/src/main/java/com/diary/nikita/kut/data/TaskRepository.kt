package com.diary.nikita.kut.data

import android.app.Application

class TaskRepository(application: Application) {
    val taskDao: TaskDao


    init {
        val dataBase = DataBase.getInstance(application.applicationContext)
        taskDao = dataBase!!.taskDao()
    }
}