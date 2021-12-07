package com.example.mvctodor.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mvctodor.models.ListModel

@Dao
interface ListDao {
    @Insert
    fun addList(listModel: ListModel)

    @Query("select * from listmodel")
    fun getAllList(): List<ListModel>

    @Update
    fun editList(listModel: ListModel)
}
