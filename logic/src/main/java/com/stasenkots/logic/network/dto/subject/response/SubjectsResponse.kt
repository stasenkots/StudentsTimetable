package com.stasenkots.logic.network.dto.subject.response


import com.google.gson.annotations.SerializedName

data class SubjectsResponse(
    @SerializedName("results")
    val subjectResponses: List<SubjectResponse>? = null
)