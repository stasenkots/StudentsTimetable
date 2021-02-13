package com.stasenkots.logic.network.dto.lesson.response

import com.google.gson.annotations.SerializedName
import com.stasenkots.logic.network.dto.DayResponse


data class LessonResponse(
    @SerializedName("day")
    val day: DayResponse? = null,
    @SerializedName("subject_id")
    val subjectId: String? = null,
    @SerializedName("updatedAt")
    val updatedAt:String?=null,
    @SerializedName("link")
    val link:String? = null,
    @SerializedName("objectId")
    val objectId: String? = null,
    @SerializedName("room")
    val room: String? = null,
    @SerializedName("time_end")
    val timeEnd: String? = null,
    @SerializedName("time_start")
    val timeStart: String? = null
)
