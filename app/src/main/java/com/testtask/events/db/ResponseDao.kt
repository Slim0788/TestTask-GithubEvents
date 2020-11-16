package com.testtask.events.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.testtask.events.api.dto.ResponseDto

@Dao
interface ResponseDao {

    @Query("SELECT * FROM ResponseDto ORDER BY id DESC")
    fun getAll(): List<ResponseDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(events: List<ResponseDto>)

    @Query("DELETE FROM ResponseDto")
    fun deleteAll()
}