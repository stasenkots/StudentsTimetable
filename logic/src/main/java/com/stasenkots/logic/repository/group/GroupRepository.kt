package com.stasenkots.logic.repository.group

import javax.inject.Inject

class GroupRepository @Inject constructor(private val groupDataSource: GroupDataSource) {
    fun hasAnyGroupWithID(id: String): Boolean {
        return groupDataSource.hasAnyGroupWithID(id)
    }
    fun saveGroup() {
        groupDataSource.saveGroup()
    }
}