package com.stasenkots.logic.repository.group

import com.stasenkots.logic.entity.User
import com.stasenkots.logic.network.dto.group_id.request.GroupRequest
import com.stasenkots.logic.network.networking.GroupApi
import javax.inject.Inject

class GroupDataSource @Inject constructor(private val api:GroupApi) {
    suspend fun hasAnyGroupWithID(id: String): Boolean {
        val response=api.getGroup("{\"group_id\": \"$id\"}")
        if (response.isSuccessful){
            val groupResponse=response.body()
            return groupResponse?.groupResponses?.isNotEmpty()?:false
        }
        else throw Exception(response.message())
    }
    suspend fun saveGroup() {
        val groupRequest = GroupRequest(User.groupId)
        api.putGroup(groupRequest)
    }
}