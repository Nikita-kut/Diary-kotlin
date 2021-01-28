package com.diary.nikita.kut

import android.app.Application
import androidx.room.Room
import com.diary.nikita.kut.data.DataBase
import com.diary.nikita.kut.data.TaskDao

class App : Application() {

    var dataBase: DataBase? = null
    var taskDao: TaskDao? = null

    override fun onCreate() {
        super.onCreate()

        instance = this

        dataBase = Room.databaseBuilder(applicationContext, DataBase::class.java, "db-name")
            .allowMainThreadQueries().build()

        taskDao = dataBase?.TaskDao()

    }

    companion object {
        var instance: App? = null
            private set
    }

}