package com.stasenkots.logic.network.manager

import com.stasenkots.logic.domain.lesson.LoadLessonsUseCase
import com.stasenkots.logic.domain.states.LoadStatesUseCase
import com.stasenkots.logic.domain.student.LoadStudentsUseCase
import com.stasenkots.logic.domain.subject.LoadSubjectsUseCase
import com.stasenkots.logic.entity.lesson.Lessons
import com.stasenkots.logic.entity.state.States
import com.stasenkots.logic.entity.student.Students
import com.stasenkots.logic.entity.subject.Subjects

class ServerLoader {
    private val loadSubjectsUseCase = LoadSubjectsUseCase()
    private val loadStatesUseCase = LoadStatesUseCase()
    private val loadStudentUseCase = LoadStudentsUseCase()
    private val loadLessonsUseCase = LoadLessonsUseCase()
    suspend fun doWork(){
        val subjects = loadSubjectsUseCase.doWork()
        Subjects.put(subjects)
        val lessons = loadLessonsUseCase.doWork()
        Lessons.put(lessons)
        val states = loadStatesUseCase.doWork()
        States.put(states)
        val students = loadStudentUseCase.doWork()
        Students.put(students)
    }
}