package com.diary.nikita.kut.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "Title") var title: String?,
    @ColumnInfo(name = "Description") var description: String?,
    @ColumnInfo(name = "CreatedAt") var createdAt: Long,
    @ColumnInfo(name = "Done") var done: Boolean
) : Parcelable {

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        title = parcel.readString(),
        description = parcel.readString(),
        createdAt = parcel.readLong(),
        done = parcel.readBoolean()
    )


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeLong(createdAt)
        parcel.writeBoolean(done)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Task> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(source: Parcel): Task = Task(source)

        override fun newArray(size: Int): Array<Task?> = arrayOfNulls(size)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (createdAt != other.createdAt) return false
        if (done != other.done) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + done.hashCode()
        return result
    }


}