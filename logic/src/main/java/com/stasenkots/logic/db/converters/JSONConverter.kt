package com.stasenkots.logic.db.converters

import androidx.room.TypeConverter
import org.json.JSONObject

class JSONConverter {
    @TypeConverter
    fun convert(json:JSONObject):String = json.toString()
    @TypeConverter
    fun convert(jsonString:String):JSONObject=JSONObject(jsonString)
}