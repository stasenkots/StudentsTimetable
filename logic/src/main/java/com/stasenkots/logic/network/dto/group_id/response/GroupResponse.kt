package com.stasenkots.logic.network.dto.group_id.response


import com.google.gson.annotations.SerializedName

data class GroupResponse(
    @SerializedName("group_id")
    val groupId: String? = null,
    @SerializedName("objectId")
    val objectId: String? = null,
    @SerializedName("start_sem_date")
    val date: String? = null
)