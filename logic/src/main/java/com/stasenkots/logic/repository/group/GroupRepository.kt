package com.stasenkots.logic.repository.group

import java.time.LocalDate
import javax.inject.Inject

class GroupRepository @Inject constructor(private val groupDataSource: GroupDataSource) {
    suspend fun hasAnyGroupWithID(id: String): Boolean {
        return groupDataSource.hasAnyGroupWithID(id)
    }
    suspend fun saveGroup(groupId:String,date:LocalDate) {
        groupDataSource.saveGroup(groupId,date)
    }
}