package com.stasenkots.logic.repository.group

import com.stasenkots.logic.entity.Group
import com.stasenkots.logic.network.mappers.GroupMapper
import java.time.LocalDate
import javax.inject.Inject

class GroupRepository @Inject constructor(
    private val dataSource: GroupDataSource,
    private val mapper:GroupMapper
) {
    suspend fun hasAnyGroupWithID(id: String): Boolean {
        return dataSource.hasAnyGroupWithID(id)
    }

    suspend fun saveGroup(groupId: String, date: LocalDate) {
        dataSource.saveGroup(groupId, date)
    }

    suspend fun getGroup(id: String): Group {
        return mapper.map(dataSource.getGroup(id))
    }

}