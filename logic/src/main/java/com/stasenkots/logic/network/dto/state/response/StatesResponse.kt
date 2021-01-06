package com.stasenkots.logic.network.dto.state.response


import com.google.gson.annotations.SerializedName

data class StatesResponse(
    @SerializedName("results")
    val statesResponse: List<StateResponse?>? = null
)