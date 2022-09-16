package com.app.common.data.database

import android.content.Context
import androidx.room.Room
class DataBaseClient {

    //Automatic Singleton Class is (companion object)
    companion object {
        private var INSTANCE: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase {
            if (INSTANCE == null) {
                synchronized(AppDataBase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "TodoDataBase").build()
    }
}