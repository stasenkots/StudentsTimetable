package com.stasenkots.logic.network.dto.state.request

import com.google.gson.annotations.SerializedName
import com.stasenkots.logic.entity.User

data class StateRequest (
    @SerializedName("comment")
    val comment: String? = null,
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("homework")
    val homework: String? = null,
    @SerializedName("subject_id")
    val subjectId: String? = null,
    @SerializedName("absent_users")
    val absentUsers: List<String>? = null,
    @SerializedName("group_id")
    val groupId:String=User.groupId
)