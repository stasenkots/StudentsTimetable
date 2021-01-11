package com.stasenkots.logic.db.mappers

import com.stasenkots.logic.db.entity.StateDb
import com.stasenkots.logic.entity.state.State
import com.stasenkots.logic.utils.toDate
import com.stasenkots.logic.utils.parseToString
import org.json.JSONArray
import javax.inject.Inject

class StateMapper @Inject constructor() {
    fun map(from: StateDb) =
        State(
            id = from.objectId,
            homework = from.homework,
            comment = from.comment,
            absentUsers = parseFromJSONArray(from.absentUsers),
            updatedAt = from.updatedAt,
            date = from.date.toDate(),
            subject = from.subjectId
        )

    fun map(from: State) = StateDb(
        date = from.date.parseToString("yyyyMMdd"),
        homework = from.homework,
        subjectId = from.subject,
        updatedAt = from.updatedAt,
        comment = from.comment,
        absentUsers = mapToJSONArray(from.absentUsers),
        objectId = from.id
    )

    private fun mapToJSONArray(absentUsers: List<String>): JSONArray {
        return JSONArray(absentUsers)
    }

    private fun parseFromJSONArray(absentUsers: JSONArray): List<String> {
        val list = mutableListOf<String>()
        if (absentUsers.length() > 0) {
            for (index in 0 until absentUsers.length()) {
                list.add(absentUsers[index].toString())
            }
        }
        return list
    }
}