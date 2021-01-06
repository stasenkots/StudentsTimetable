package com.stasenkots.logic.network.networking


import com.stasenkots.logic.entity.User
import com.stasenkots.logic.network.dto.lesson.request.LessonRequest
import com.stasenkots.logic.network.dto.lesson.response.LessonResponse
import com.stasenkots.logic.network.dto.lesson.response.LessonsResponse
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface LessonApi {

    @GET("/classes/lesson/")
    suspend fun getLessons(): Response<LessonsResponse>

    @POST("/classes/lesson/")
    suspend fun putLesson(
        @Body
        body: LessonRequest
    )
    @PUT("/classes/lesson/{objectId}")
    suspend fun updateLesson(
        @Path("objectId")
        objectId: String,
        @Body
        body: LessonRequest,
    )
    @DELETE("/classes/lesson/{objectId}")
    suspend fun deleteLesson(
        @Path("objectId")
        objectId: String
    )
}