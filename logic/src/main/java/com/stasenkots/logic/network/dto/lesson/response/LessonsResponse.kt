package com.stasenkots.logic.network.dto.lesson.response


import com.google.gson.annotations.SerializedName

data class LessonsResponse(
    @SerializedName("results")
    val lessonResponses: List<LessonResponse>? = null
)