package com.stasenkots.logic.network.dto.subject.request

import com.google.gson.annotations.SerializedName

class SubjectRequest(
    @SerializedName("group_id")
    val groupId: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("subgroup")
    val subgroup: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("teacher")
    val teacher: String? = null
)