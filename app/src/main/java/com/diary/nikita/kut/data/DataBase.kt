package com.diary.nikita.kut.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.diary.nikita.kut.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        var INSTANCE: DataBase? = null
            private set

        fun getInstance(context: Context): DataBase? {
            if (INSTANCE == null) {
                synchronized(DataBase::class.java) {
                    INSTANCE = Room.databaseBuilder(context, DataBase::class.java, "task_data_base")
                        .build()
                }
            }
            return INSTANCE
        }
    }
}