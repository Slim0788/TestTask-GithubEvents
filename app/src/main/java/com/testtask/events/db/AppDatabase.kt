package com.testtask.events.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.testtask.events.api.dto.ResponseDto

@Database(entities = [ResponseDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun responseDao(): ResponseDao
}