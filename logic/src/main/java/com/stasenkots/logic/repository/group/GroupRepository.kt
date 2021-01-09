package com.stasenkots.logic.repository.group

import javax.inject.Inject

class GroupRepository @Inject constructor(private val groupDataSource: GroupDataSource) {
    suspend fun hasAnyGroupWithID(id: String): Boolean {
        return groupDataSource.hasAnyGroupWithID(id)
    }
    suspend fun saveGroup() {
        groupDataSource.saveGroup()
    }
}