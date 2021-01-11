package com.stasenkots.logic.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.stasenkots.logic.db.converters.JSONArrayConverter
import org.json.JSONArray
import org.json.JSONObject

@Entity(tableName = "state_table")
@TypeConverters(JSONArrayConverter::class)
data class StateDb (
    @PrimaryKey
    val objectId: String,
    val comment: String,
    val updatedAt:Long,
    val date: String,
    val homework: String,
    val subjectId: String,
    val absentUsers: JSONArray
)