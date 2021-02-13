package com.stasenkots.logic.network.mappers

import com.stasenkots.logic.entity.*
import com.stasenkots.logic.entity.lesson.Lesson
import com.stasenkots.logic.entity.state.States
import com.stasenkots.logic.entity.subject.Subject
import com.stasenkots.logic.entity.subject.Subjects
import java.time.LocalDate
import javax.inject.Inject

class LessonItemMapper @Inject constructor() {
    fun map(lesson: Lesson, date: LocalDate) :LessonItem{
        val subject = Subjects.get()
            .values.find { subject->
                subject.id == lesson.subject
            } ?: Subject()

        val state= States.get().values.find {
            date==it.date
                    && subject.id == it.subject
        }
        return LessonItem(
            lesson = lesson.id,
            subject = subject.id,
            name = subject.name,
            type = subject.type,
            teacher = subject.teacher,
            date = date,
            link = lesson.link,
            subgroup = subject.subgroup,
            timeStart = lesson.timeStart,
            timeEnd = lesson.timeEnd,
            room = lesson.room,
            dayOfWeek = lesson.dayOfWeek,
            state = state
        )

    }
    fun mapToLesson(from:LessonItem): Lesson {
        return Lesson(
            id=from.lesson,
            subject = from.subject,
            timeStart = from.timeStart,
            timeEnd = from.timeEnd,
            room = from.room,
            dayOfWeek = from.dayOfWeek,
            link = from.link
        )
    }
    fun mapToSubject(from:LessonItem): Subject {
        return Subject(
            id=from.subject,
            name = from.name,
            teacher = from.teacher,
            subgroup = from.subgroup,
            type = from.type
        )
    }
}