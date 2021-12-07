package com.example.mvctodor.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ServiceDao {
    @Insert
    fun addTask(taskModel: TaskModel)


    @Query("select * from taskmodel")
    fun getAllTask(): List<TaskModel>

    @Update
    fun editTask(taskModel: TaskModel)

    @Query("select * from taskmodel where listName=:name")
    fun getAllListName(name: String): List<TaskModel>


}