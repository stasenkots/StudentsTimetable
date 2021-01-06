package com.stasenkots.logic.db.converters

import androidx.room.TypeConverter
import org.json.JSONArray
import org.json.JSONObject

class JSONArrayConverter {
    @TypeConverter
    fun convert(json: JSONArray):String = json.toString()
    @TypeConverter
    fun convert(jsonString:String): JSONArray = JSONArray(jsonString)
}