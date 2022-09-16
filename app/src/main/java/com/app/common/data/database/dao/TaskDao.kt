package com.app.common.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.common.model.RoomModel

@Dao
interface TaskDao {
    @Query("SELECT * FROM RoomModel")
    fun getAll(): LiveData<List<RoomModel>>

    @Insert
    suspend fun insert(vararg task: RoomModel): LongArray

    @Update
    suspend fun update(vararg task: RoomModel)

    @Delete
     fun delete(task: RoomModel)

    //Delete one item by id
    @Query("DELETE FROM RoomModel WHERE id = :itemId")
    fun deleteByItemId(itemId: Long)
}