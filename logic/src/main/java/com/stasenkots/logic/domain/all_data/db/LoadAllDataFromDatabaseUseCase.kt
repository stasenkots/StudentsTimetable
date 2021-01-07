package com.stasenkots.logic.domain.all_data.db

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.lesson.Lessons
import com.stasenkots.logic.entity.state.States
import com.stasenkots.logic.entity.student.Students
import com.stasenkots.logic.entity.subject.Subjects

class LoadAllDataFromDatabaseUseCase : DatabaseUseCase() {
    init {
        DaggerLogicComponent
            .create()
            .initializeLoadAllDataFromDatabaseUseCase(this)
    }

    override suspend fun doWork(params:Params) {
        val subjects =
            subjectRepository.getSubjectsFromDb(params.subjectDao)
        if (Subjects.get().isEmpty() && subjects.isNotEmpty()) {
            Subjects.put(subjects)
        }
        val lessons = lessonRepository.getLessonsFromDb(params.lessonDao)
        if (Lessons.get().isEmpty() && lessons.isNotEmpty()) {
            Lessons.put(lessons)
        }
        val states = stateRepository.getStateFromDb(params.stateDao)
        if (States.get().isEmpty() && states.isNotEmpty()) {
            States.put(states)
        }
        val students = studentRepository.getStudentsFromDb(params.studentDao)
        if (Students.get().isEmpty() && students.isNotEmpty()) {
            Students.put(students)
        }
    }
}