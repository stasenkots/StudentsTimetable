package com.stasenkots.logic.network.mappers

import com.stasenkots.logic.entity.student.Student
import com.stasenkots.logic.network.dto.student.StudentResponse
import com.stasenkots.logic.utils.toLong
import java.text.SimpleDateFormat
import java.time.LocalDate
import javax.inject.Inject

class StudentMapper @Inject constructor() {
    fun map(from: StudentResponse): Student {
        return Student(
            id = from.objectId ?: throw Exception("invalid student id"),
            name = from.name.orEmpty(),
            updatedAt = from.updatedAt.orEmpty().toLong()
        )
    }
}