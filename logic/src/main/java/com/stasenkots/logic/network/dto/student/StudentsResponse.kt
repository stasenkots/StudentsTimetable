package com.stasenkots.logic.network.dto.student


import com.google.gson.annotations.SerializedName

data class StudentsResponse(
    @SerializedName("results")
    val studentResponses: List<StudentResponse>? = null
)