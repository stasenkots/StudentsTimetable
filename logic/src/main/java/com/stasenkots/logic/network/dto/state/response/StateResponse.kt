package com.stasenkots.logic.network.dto.state.response

import com.google.gson.annotations.SerializedName

data class StateResponse (
    @SerializedName("comment")
    val comment: String? = null,
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("homework")
    val homework: String? = null,
    @SerializedName("objectId")
    val objectId: String? = null,
    @SerializedName("subject_id")
    val subjectId: String? = null,
    @SerializedName("absent_users")
    val absentUsers: List<String>? = null
)