package com.stasenkots.logic.db.manager

import com.stasenkots.logic.db.dao.LessonDao
import com.stasenkots.logic.db.dao.StateDao
import com.stasenkots.logic.db.dao.StudentDao
import com.stasenkots.logic.db.dao.SubjectDao
import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.domain.student.LoadStudentsFromDbUseCase
import com.stasenkots.logic.entity.lesson.Lessons
import com.stasenkots.logic.entity.state.States
import com.stasenkots.logic.entity.student.Students
import com.stasenkots.logic.entity.subject.Subjects

class DataBaseLoader(
    lessonDao: LessonDao,
    subjectDao: SubjectDao,
    stateDao: StateDao,
    studentDao: StudentDao
) : DatabaseWorker(lessonDao, subjectDao, stateDao, studentDao) {
    init {
        DaggerLogicComponent
            .create()
            .initializeDatabaseLoader(this)
    }

    override suspend fun doWork() {
        val subjects =
            subjectRepository.getSubjectsFromDb(subjectDao)
        if (Subjects.get().isEmpty() && subjects.isNotEmpty()) {
            Subjects.put(subjects)
        }
        val lessons = lessonRepository.getLessonsFromDb(lessonDao)
        if (Lessons.get().isEmpty() && lessons.isNotEmpty()) {
            Lessons.put(lessons)
        }
        val states = stateRepository.getStateFromDb(stateDao)
        if (States.get().isEmpty() && states.isNotEmpty()) {
            States.put(states)
        }
        val students = studentRepository.getStudentsFromDb(studentDao)
        if (Students.get().isEmpty() && students.isNotEmpty()) {
            Students.put(students)
        }
    }
}