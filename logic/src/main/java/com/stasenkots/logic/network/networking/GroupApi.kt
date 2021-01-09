package com.stasenkots.logic.network.networking


import com.stasenkots.logic.network.dto.group_id.request.GroupRequest
import com.stasenkots.logic.network.dto.group_id.response.GroupResponse
import com.stasenkots.logic.network.dto.group_id.response.GroupsResponse
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
    ): Response<GroupsResponse>

    @POST("/classes/group_ids/")
    suspend fun putGroup(
        @Body
        body: GroupRequest
    )
}