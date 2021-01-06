package com.stasenkots.logic.db.manager

import com.stasenkots.logic.db.dao.LessonDao
import com.stasenkots.logic.db.dao.StateDao
import com.stasenkots.logic.db.dao.StudentDao
import com.stasenkots.logic.db.dao.SubjectDao
import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.lesson.Lessons
import com.stasenkots.logic.entity.state.States
import com.stasenkots.logic.entity.student.Students
import com.stasenkots.logic.entity.subject.Subjects
import com.stasenkots.logic.utils.launchIO


class DatabaseSaver(
    lessonDao: LessonDao,
    subjectDao: SubjectDao,
    stateDao: StateDao,
    studentDao: StudentDao
) : DatabaseWorker(lessonDao, subjectDao, stateDao, studentDao) {
    init {
        DaggerLogicComponent
            .create()
            .initializeDatabaseSaver(this)
    }

    override suspend fun doWork() {
        lessonRepository.saveLessonsToDb(
            lessonDao,
            Lessons.get().values.toList()
        )

        stateRepository.saveStatesToDb(
            stateDao,
            States.get().values.toList()
        )
        studentRepository.saveStudentsToDb(
            studentDao,
            Students.get().values.toList()
        )
        subjectRepository.saveSubjectsToDb(
            subjectDao,
            Subjects.get().values.toList()
        )
    }
}