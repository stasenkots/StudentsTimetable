package com.stasenkots.logic.network.dto.lesson.request

import com.google.gson.annotations.SerializedName
import com.stasenkots.logic.network.dto.DayResponse

class LessonRequest(
    @SerializedName("day")
    val day: DayResponse? = null,
    @SerializedName("subject_id")
    val subjectId: String? = null,
    @SerializedName("room")
    val room: String? = null,
    @SerializedName("time_end")
    val timeEnd: String? = null,
    @SerializedName("time_start")
    val timeStart: String? = null,
    @SerializedName("group_id")
    val groupId:String?=null
) {
}