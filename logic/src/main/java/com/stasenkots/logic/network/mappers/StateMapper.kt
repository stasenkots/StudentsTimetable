package com.stasenkots.logic.network.mappers

import com.parse.ParseObject
import com.stasenkots.logic.entity.state.State
import com.stasenkots.logic.network.dto.state.request.StateRequest
import com.stasenkots.logic.network.dto.state.response.StateResponse
import com.stasenkots.logic.utils.toDate
import com.stasenkots.logic.utils.parseToString
import com.stasenkots.logic.utils.toLong
import java.text.SimpleDateFormat
import java.time.LocalDate
import javax.inject.Inject

class StateMapper @Inject constructor() {

    fun map(from: StateResponse?) =
        State(
            id = from?.objectId ?: throw Exception("Null state id"),
            homework = from.homework.orEmpty(),
            comment = from.comment.orEmpty(),
            absentUsers = from.absentUsers ?: emptyList(),
            date = from.date?.toDate() ?: LocalDate.now(),
            updatedAt = from.updatedAt.orEmpty().toLong(),
            subject = from.subjectId.orEmpty()
        )

    fun map(from: ParseObject?) =
        State(
            id = from?.objectId ?: throw Exception("Null state id"),
            homework = from.getString("homework").orEmpty(),
            comment = from.getString("comment").orEmpty(),
            absentUsers = from.getList("absent_users") ?: emptyList(),
            updatedAt = from.updatedAt.time,
            date = from.getString("date")?.toDate() ?: LocalDate.now(),
            subject = from.getString("subject_id").orEmpty()
        )

    fun map(from: State) = StateRequest(
        date = from.date.parseToString("yyyyMMdd"),
        homework = from.homework,
        subjectId = from.subject,
        comment = from.comment,
        absentUsers = from.absentUsers
    )

}