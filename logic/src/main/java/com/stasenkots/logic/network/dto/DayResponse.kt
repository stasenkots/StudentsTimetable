package com.stasenkots.logic.network.dto

import com.google.gson.annotations.SerializedName

data class DayResponse(
    @SerializedName("day")
    val day:Int?=null,
    @SerializedName("week")
    val week:Int?=null
)