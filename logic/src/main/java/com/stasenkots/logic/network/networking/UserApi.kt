package com.stasenkots.logic.network.networking

import retrofit2.http.GET

interface UserApi {
    @GET("/users/me")
    suspend fun getCurrentUser()
}