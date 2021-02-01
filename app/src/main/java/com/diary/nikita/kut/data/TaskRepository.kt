package com.diary.nikita.kut.data

import android.app.Application

class TaskRepository(application: Application) {

    private val taskDao: TaskDao


    init {
        val dataBase = DataBase.getInstance(application.applicationContext)
        taskDao = dataBase!!.taskDao()
    }
}