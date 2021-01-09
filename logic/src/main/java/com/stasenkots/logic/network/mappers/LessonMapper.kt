package com.stasenkots.logic.network.mappers

import com.parse.ParseObject
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.entity.lesson.Lesson
import com.stasenkots.logic.network.dto.lesson.request.LessonRequest
import com.stasenkots.logic.network.dto.lesson.response.LessonResponse
import java.lang.Exception
import javax.inject.Inject

class LessonMapper @Inject constructor(private val dayOfWeekMapper: DayOfWeekMapper ){
    fun map(
        lessonResponse: LessonResponse?,
    ) = Lesson(
        timeStart = lessonResponse?.timeStart.orEmpty(),
        timeEnd = lessonResponse?.timeEnd.orEmpty(),
        room = lessonResponse?.room.orEmpty(),
        dayOfWeek = dayOfWeekMapper.map(lessonResponse?.day),
        id = lessonResponse?.objectId.orEmpty(),
        updatedAt = lessonResponse?.updatedAt.orEmpty(),
        subject = lessonResponse?.subjectId.orEmpty()
    )
    fun map(
        lesson: Lesson,
    ) = LessonRequest(
        timeStart = lesson.timeStart,
        timeEnd = lesson.timeEnd,
        room = lesson.room,
        subjectId = lesson.subject,
        day = dayOfWeekMapper.map(lesson.dayOfWeek),
        groupId = User.groupId
    )

    fun map(from: ParseObject?)= Lesson(
        id = from?.objectId?:throw Exception("Empty lesson id"),
        subject = from.getString("subject_id").orEmpty(),
        timeStart = from.getString("time_start").orEmpty(),
        timeEnd = from.getString("time_end").orEmpty(),
        room = from.getString("room").orEmpty(),
        updatedAt = from.getString("updatedAt").orEmpty(),
        dayOfWeek =dayOfWeekMapper.map(from.getJSONObject("day"))
    )
}