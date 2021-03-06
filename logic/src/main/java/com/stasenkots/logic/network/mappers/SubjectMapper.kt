package com.stasenkots.logic.network.mappers

import com.parse.ParseObject
import com.stasenkots.logic.entity.subject.Subject
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.network.dto.subject.request.SubjectRequest
import com.stasenkots.logic.network.dto.subject.response.SubjectResponse
import com.stasenkots.logic.utils.toLong
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import javax.inject.Inject

class SubjectMapper @Inject constructor(

) {
    fun map(from:SubjectResponse?): Subject {
        return Subject(
            id = from?.objectId.orEmpty(),
            name = from?.name.orEmpty(),
            type = from?.type.orEmpty(),
            updatedAt = from?.updatedAt.orEmpty().toLong(),
            teacher = from?.teacher.orEmpty(),
            subgroup = from?.subgroup.orEmpty()
        )
    }
    fun map(from: Subject):SubjectRequest{
        return SubjectRequest(
            name = from.name,
            type = from.type,
            teacher = from.teacher,
            subgroup = from.subgroup,
            groupId = User.groupId
        )
    }
    fun map(from:ParseObject?)= Subject(
        id=from?.objectId?:throw Exception("Subject without id"),
        name = from.getString("name").orEmpty(),
        updatedAt = from.updatedAt.time,
        teacher = from.getString("teacher").orEmpty(),
        subgroup = from.getString("subgroup").orEmpty(),
        type = from.getString("type").orEmpty()
    )
}