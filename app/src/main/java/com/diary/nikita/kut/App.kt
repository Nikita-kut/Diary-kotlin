package com.diary.nikita.kut

import android.app.Application
import androidx.room.Room
import com.diary.nikita.kut.data.DataBase
import com.diary.nikita.kut.data.TaskDao

class App : Application() {

    lateinit var dataBase: DataBase
    lateinit var taskDao: TaskDao

    companion object {
        lateinit var instance: App
    }

    fun getInstanceCustom(): App {
        return instance
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        dataBase = Room.databaseBuilder(applicationContext, DataBase::class.java, "db-name")
            .allowMainThreadQueries().build()

        taskDao = dataBase.TaskDao()

    }


}