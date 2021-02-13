package com.stasenkots.logic.network.mappers

import com.parse.ParseObject
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.entity.lesson.Lesson
import com.stasenkots.logic.network.dto.lesson.request.LessonRequest
import com.stasenkots.logic.network.dto.lesson.response.LessonResponse
import com.stasenkots.logic.utils.toLong
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import javax.inject.Inject

class LessonMapper @Inject constructor(
    private val dayOfWeekMapper: DayOfWeekMapper,
) {
    fun map(
        from: LessonResponse?,
    ) = Lesson(
        timeStart = from?.timeStart.orEmpty(),
        timeEnd = from?.timeEnd.orEmpty(),
        room = from?.room.orEmpty(),
        dayOfWeek = dayOfWeekMapper.map(from?.day),
        id = from?.objectId.orEmpty(),
        updatedAt = from?.updatedAt.orEmpty().toLong(),
        subject = from?.subjectId.orEmpty(),
        link = from?.link.orEmpty()
    )

    fun map(
        lesson: Lesson,
    ) = LessonRequest(
        timeStart = lesson.timeStart,
        timeEnd = lesson.timeEnd,
        room = lesson.room,
        link = lesson.link,
        subjectId = lesson.subject,
        day = dayOfWeekMapper.map(lesson.dayOfWeek),
        groupId = User.groupId
    )

    fun map(from: ParseObject?) = Lesson(
        id = from?.objectId ?: throw Exception("Empty lesson id"),
        subject = from.getString("subject_id").orEmpty(),
        timeStart = from.getString("time_start").orEmpty(),
        timeEnd = from.getString("time_end").orEmpty(),
        room = from.getString("room").orEmpty(),
        updatedAt = from.updatedAt.time,
        link = from.getString("link").orEmpty(),
        dayOfWeek = dayOfWeekMapper.map(from.getJSONObject("day"))
    )
}