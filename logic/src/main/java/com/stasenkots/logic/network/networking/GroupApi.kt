package com.stasenkots.logic.network.networking


import com.stasenkots.logic.network.dto.group.request.GroupRequest
import com.stasenkots.logic.network.dto.group.response.GroupResponses
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GroupApi {

    @GET("/classes/group_ids/")
    suspend fun getGroup(
        @Query("where")
        groupIdRequest: String
    ): Response<GroupResponses>

    @POST("/classes/group_ids/")
    suspend fun putGroup(
        @Body
        body: GroupRequest
    )
}