package com.stasenkots.logic.repository.group

import com.stasenkots.logic.network.dto.group.request.GroupRequest
import com.stasenkots.logic.network.dto.group.response.GroupResponse
import com.stasenkots.logic.network.networking.GroupApi
import com.stasenkots.logic.utils.parseToString
import java.time.LocalDate
import javax.inject.Inject

class GroupDataSource @Inject constructor(private val api: GroupApi) {
    suspend fun hasAnyGroupWithID(id: String): Boolean {
        val response = api.getGroup("{\"group_id\": \"$id\"}")
        if (response.isSuccessful) {
            val groupResponse = response.body()
            return groupResponse?.groupResponses?.isNotEmpty() ?: false
        } else throw Exception(response.message())
    }

    suspend fun getGroup(id: String): GroupResponse?{
        val response = api.getGroup("{\"group_id\": \"$id\"}")
        if (response.isSuccessful) {
            return response.body()?.groupResponses?.get(0)
        } else throw Exception(response.message())
    }

    suspend fun saveGroup(groupId: String, date: LocalDate) {
        val groupRequest = GroupRequest(groupId, date.parseToString("yyyyMMdd"))
        api.putGroup(groupRequest)
    }
}