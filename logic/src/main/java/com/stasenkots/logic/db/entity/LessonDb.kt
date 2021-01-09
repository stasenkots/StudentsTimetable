package com.stasenkots.logic.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.JsonObject
import com.stasenkots.logic.db.converters.JSONConverter
import com.stasenkots.logic.network.dto.DayResponse
import org.json.JSONObject

@Entity(tableName = "lesson_table")
@TypeConverters(JSONConverter::class)
class LessonDb(
    @PrimaryKey
    val objectId: String,
    val dayOfWeek: JSONObject,
    val subjectId: String,
    val updatedAt:String,
    val room: String,
    val timeEnd: String,
    val timeStart: String

)