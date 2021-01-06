package com.stasenkots.logic.network.networking

import com.stasenkots.logic.entity.User
import com.stasenkots.logic.network.dto.state.request.StateRequest
import com.stasenkots.logic.network.dto.state.response.StateResponse
import com.stasenkots.logic.network.dto.state.response.StatesResponse
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface StateApi {
    @GET("/classes/state/")
    suspend fun getStates(): Response<StatesResponse>

    @POST("/classes/state/")
    suspend fun putState(
        @Body
        state: StateRequest
    )

    @PUT("/classes/state/{objectId}")
    suspend fun setState(
        @Path("objectId")
        objectId:String,
        @Body
        state: StateRequest
    )
    @DELETE("/classes/state/{objectId}")
    suspend fun deleteState(
        @Path("objectId")
        objectId: String
    )
}