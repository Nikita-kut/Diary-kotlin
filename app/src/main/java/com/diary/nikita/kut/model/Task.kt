package com.diary.nikita.kut.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Task")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "Title") var title: String?,
    @ColumnInfo(name = "Description") var description: String?,
    @ColumnInfo(name = "CreatedAt") var createdAt: Long,
    @ColumnInfo(name = "Done") var done: Boolean
) : Parcelable
