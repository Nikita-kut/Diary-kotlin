package com.diary.nikita.kut.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.diary.nikita.kut.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM Task")
    fun getAll(): List<Task>

    @Query("SELECT * FROM Task")
    fun getAllLiveData(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE id IN (:taskIds)")
    fun loadAllByIds(taskIds: IntArray): List<Task>

    @Query("SELECT * FROM Task WHERE id = :id LIMIT 1")
    fun findById(id: Int): Task

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)
}