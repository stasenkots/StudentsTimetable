package com.stasenkots.logic.db.mappers

import com.stasenkots.logic.db.entity.LessonDb
import com.stasenkots.logic.db.entity.SubjectDb
import com.stasenkots.logic.entity.*
import com.stasenkots.logic.entity.lesson.Lesson
import com.stasenkots.logic.entity.state.States
import com.stasenkots.logic.entity.subject.Subject
import com.stasenkots.logic.entity.subject.Subjects
import java.time.LocalDate
import javax.inject.Inject

class LessonItemDbMapper @Inject constructor(
    private val dayOfWeekDbMapper: DayOfWeekDbMapper,
) {
    fun map(lesson: LessonDb, date: LocalDate) :LessonItem{
        val subject = Subjects.get()
            .values.find { subject->
                subject.id == lesson.subjectId
            } ?: Subject()

        val state= States.get().values.find {
            date==it.date
                    && subject.id == it.subject
        }
        return LessonItem(
            lesson = lesson.objectId,
            subject = subject.id,
            name = subject.name,
            type = subject.type,
            teacher = subject.teacher,
            date = date,
            subgroup = subject.subgroup,
            timeStart = lesson.timeStart,
            timeEnd = lesson.timeEnd,
            room = lesson.room,
            dayOfWeek = dayOfWeekDbMapper.map(lesson.dayOfWeek),
            state = state
        )

    }
    fun mapToLessonDb(from:LessonItem): LessonDb {
        return LessonDb(
            objectId= from.lesson,
            subjectId = from.subject,
            timeStart = from.timeStart,
            timeEnd = from.timeEnd,
            room = from.room,
            dayOfWeek = dayOfWeekDbMapper.map(from.dayOfWeek),
        )
    }
    fun mapToSubjectDb(from:LessonItem): SubjectDb {
        return SubjectDb(
            objectId= from.subject,
            name = from.name,
            teacher = from.teacher,
            subgroup = from.subgroup,
            type = from.type
        )
    }
}