package com.example.demo.dao

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by tanqing.cc
 * on 2021/7/12
 */

@Database(entities = [TodoListEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): TodoListDao
}