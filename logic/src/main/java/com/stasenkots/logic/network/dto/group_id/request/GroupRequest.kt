package com.stasenkots.logic.network.dto.group_id.request


import com.google.gson.annotations.SerializedName

data class GroupRequest(
    @SerializedName("group_id")
    val groupId: String? = null
)