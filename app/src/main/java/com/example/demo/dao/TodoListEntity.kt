package com.example.demo.dao

import androidx.room.*

/**
 * Created by tanqing.cc
 * on 2021/7/12
 */

@Entity(tableName = "todolist")
data class TodoListEntity(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "time")
    val time: Long,

    @ColumnInfo(name = "completed")
    var completed: Boolean
)
