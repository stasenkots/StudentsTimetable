package com.stasenkots.logic.repository.group

import com.parse.ParseObject
import com.parse.ParseQuery
import com.stasenkots.logic.entity.User
import javax.inject.Inject

class GroupDataSource @Inject constructor() {
    //TODO rewrite with retrofit
    fun hasAnyGroupWithID(id: String): Boolean {
        val query = ParseQuery.getQuery<ParseObject>("group_ids")
        query.whereEqualTo("group_id", id)
        return query.first != null
    }
    fun saveGroup() {
        val groupIds = ParseObject("group_ids")
        User.groupId?.let { groupIds.put("group_id", it) }
        groupIds.save()
    }
}