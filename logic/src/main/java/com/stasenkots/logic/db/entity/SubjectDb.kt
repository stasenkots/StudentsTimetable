package com.stasenkots.logic.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "subject_table")
data class SubjectDb(
    @PrimaryKey
    val objectId: String,
    val updatedAt:String,
    val name: String,
    val subgroup: String,
    val type: String,
    val teacher: String
)
