package com.stasenkots.logic.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "student_table")
data class StudentDb(
    val name: String,
    val updatedAt:String,
    @PrimaryKey
    val objectId: String
)