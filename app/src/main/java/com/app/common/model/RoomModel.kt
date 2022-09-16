package com.app.common.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class RoomModel: Serializable {
    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0

    @ColumnInfo(name = "Title")
    private var title: String? = null

    @ColumnInfo(name = "Description")
    private var description: String? = null

    @ColumnInfo(name = "Type")
    private var type: String? = null

    @ColumnInfo(name = "Time")
    private var time: String? = null

    @ColumnInfo(name = "Salary")
    private var salary = 0

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getDescription(): String? {
        return description
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int {
        return id
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String) {
        this.type = type
    }

    fun getTime(): String? {
        return time
    }

    fun setTime(time: String) {
        this.time = time
    }

    fun getSalary(): Int {
        return salary
    }

    fun setSalary(salary: Int) {
        this.salary = salary
    }
}




