package com.stasenkots.logic.network.dto.subject.response

import com.google.gson.annotations.SerializedName


data class SubjectResponse(
    @SerializedName("group_id")
    val groupId: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("updatedAt")
    val updatedAt:String?=null,
    @SerializedName("objectId")
    val objectId: String? = null,
    @SerializedName("subgroup")
    val subgroup: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("teacher")
    val teacher: String? = null
)
