package com.stasenkots.logic.network.dto.group_id.response

import com.google.gson.annotations.SerializedName
import com.stasenkots.logic.network.dto.lesson.response.LessonResponse

class GroupResponses {
    @SerializedName("results")
    val groupResponses: List<GroupResponse>?=null
}