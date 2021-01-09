package com.stasenkots.logic.db.mappers

import com.parse.ParseObject
import com.stasenkots.logic.db.entity.LessonDb
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.entity.lesson.Lesson
import com.stasenkots.logic.network.dto.lesson.request.LessonRequest
import java.lang.Exception
import javax.inject.Inject

class LessonMapper @Inject constructor(private val dayOfWeekDbMapper: DayOfWeekDbMapper ){
    fun map(
        from: LessonDb,
    ) = Lesson(
        timeStart = from.timeStart,
        timeEnd = from.timeEnd,
        room = from.room,
        dayOfWeek = dayOfWeekDbMapper.map(from.dayOfWeek),
        id = from.objectId,
        subject = from.subjectId,
        updatedAt = from.updatedAt
    )
    fun map(
        lesson: Lesson,
    ) = LessonDb(
        timeStart = lesson.timeStart,
        timeEnd = lesson.timeEnd,
        room = lesson.room,
        subjectId = lesson.subject,
        dayOfWeek = dayOfWeekDbMapper.map(lesson.dayOfWeek),
        objectId = lesson.id,
        updatedAt = lesson.updatedAt
    )

}