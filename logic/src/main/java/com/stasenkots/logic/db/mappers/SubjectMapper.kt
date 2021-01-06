package com.stasenkots.logic.db.mappers

import com.parse.ParseObject
import com.stasenkots.logic.db.entity.SubjectDb
import com.stasenkots.logic.entity.subject.Subject
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.network.dto.subject.request.SubjectRequest
import com.stasenkots.logic.network.dto.subject.response.SubjectResponse
import org.json.JSONArray
import java.lang.Exception
import javax.inject.Inject

class SubjectMapper @Inject constructor() {
    fun map(from: SubjectDb): Subject {
        return Subject(
            id = from.objectId,
            name = from.name,
            type = from.type,
            teacher = from.teacher,
            subgroup = from.subgroup
        )
    }
    fun map(from: Subject):SubjectDb{
        return SubjectDb(
            name = from.name,
            type = from.type,
            teacher = from.teacher,
            subgroup = from.subgroup,
            objectId = from.id
        )
    }
}