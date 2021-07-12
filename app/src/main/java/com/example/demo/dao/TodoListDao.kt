package com.example.demo.dao

import androidx.room.*

/**
 * Created by tanqing.cc
 * on 2021/7/12
 */

@Dao
interface TodoListDao {
    @Query("select * from todolist order by time desc")
    fun getAllData(): MutableList<TodoListEntity>

    @Insert
    fun addData(data: TodoListEntity)

    @Delete
    fun deleteData(data: TodoListEntity)

    @Update()
    fun updateData(data: TodoListEntity)
}