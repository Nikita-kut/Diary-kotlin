package com.diary.nikita.kut.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.diary.nikita.kut.model.Task

@Dao
interface TaskDao {


    @Query("SELECT * FROM Task ORDER BY id ASC")
    fun getActiveTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE id =:id")
    fun getTask(id: Int): Task

    @Insert
    suspend fun saveTask(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}