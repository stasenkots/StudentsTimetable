package com.stasenkots.logic.network.dto.student


import com.google.gson.annotations.SerializedName

data class StudentResponse(
    @SerializedName("group_id")
    val groupId: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("objectId")
    val objectId: String? = null,
    @SerializedName("updatedAt")
    val updatedAt:String?=null,
)