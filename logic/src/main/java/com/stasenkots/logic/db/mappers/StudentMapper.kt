package com.stasenkots.logic.db.mappers

import com.stasenkots.logic.db.entity.StudentDb
import com.stasenkots.logic.entity.student.Student
import com.stasenkots.logic.network.dto.student.StudentResponse
import javax.inject.Inject

class StudentMapper @Inject constructor(){
    fun map(from:Student): StudentDb {
        return StudentDb(
            objectId = from.id,
            name = from.name
        )
    }
    fun map(from:StudentDb):Student{
        return Student(
            id = from.objectId,
            name = from.name
        )
    }
}