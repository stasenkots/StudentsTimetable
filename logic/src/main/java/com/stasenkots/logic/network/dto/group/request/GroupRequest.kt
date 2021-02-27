package com.stasenkots.logic.network.dto.group.request


import com.google.gson.annotations.SerializedName

data class GroupRequest(
    @SerializedName("group_id")
    val groupId: String? = null,
    @SerializedName("start_sem_date")
    val date: String? = null,

)