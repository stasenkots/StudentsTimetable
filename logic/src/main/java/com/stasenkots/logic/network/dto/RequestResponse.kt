package com.stasenkots.logic.network.dto


import com.google.gson.annotations.SerializedName
data class RequestResponse(
    @SerializedName("objectId")
    val objectId: String? = null
)