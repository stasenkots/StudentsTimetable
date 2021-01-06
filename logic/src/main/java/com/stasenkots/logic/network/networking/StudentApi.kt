package com.stasenkots.logic.network.networking

import com.stasenkots.logic.entity.User
import com.stasenkots.logic.entity.student.Student
import com.stasenkots.logic.network.dto.student.StudentResponse
import com.stasenkots.logic.network.dto.student.StudentsResponse
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StudentApi {
    @GET("/users")
    suspend fun getStudents(): Response<StudentsResponse>
}