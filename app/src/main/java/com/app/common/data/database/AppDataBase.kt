package com.app.common.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.common.data.database.dao.TaskDao
import com.app.common.model.RoomModel

@Database(entities = [RoomModel::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun todoDao(): TaskDao
}