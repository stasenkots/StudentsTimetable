package com.stasenkots.logic.network.networking


import com.google.gson.JsonObject
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.network.dto.RequestResponse
import com.stasenkots.logic.network.dto.subject.request.SubjectRequest
import com.stasenkots.logic.network.dto.subject.response.SubjectsResponse
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface SubjectApi {
    @GET("/classes/subject/")
    suspend fun getSubjects(): Response<SubjectsResponse>

    @POST("/classes/subject/")
    suspend fun putSubject(
        @Body
        body: SubjectRequest
    ):Response<RequestResponse>
    @PUT("/classes/subject/{objectId}")
    suspend fun updateSubject(
        @Path("objectId")
        objectId:String,
        @Body
        body: SubjectRequest
    )

}