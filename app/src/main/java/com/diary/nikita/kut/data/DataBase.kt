package com.diary.nikita.kut.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.diary.nikita.kut.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false )
abstract class DataBase: RoomDatabase() {

    abstract fun TaskDao(): TaskDao
}