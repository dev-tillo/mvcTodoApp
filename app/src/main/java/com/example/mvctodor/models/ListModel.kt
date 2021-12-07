package com.example.mvctodor.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class ListModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var color: Int
):Serializable
