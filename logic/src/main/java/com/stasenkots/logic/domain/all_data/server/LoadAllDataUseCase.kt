package com.stasenkots.logic.domain.all_data.server

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.domain.lesson.LoadLessonsUseCase
import com.stasenkots.logic.domain.states.LoadStatesUseCase
import com.stasenkots.logic.domain.student.LoadStudentsUseCase
import com.stasenkots.logic.domain.subject.LoadSubjectsUseCase
import com.stasenkots.logic.entity.lesson.Lessons
import com.stasenkots.logic.entity.state.States
import com.stasenkots.logic.entity.student.Students
import com.stasenkots.logic.entity.subject.Subjects

class LoadAllDataUseCase: ServerUseCase(){

    init {
        DaggerLogicComponent
            .create()
            .initializeLoadAllDataUseCase(this)
    }
    override suspend fun doWork(){
        val subjects = subjectRepository.getSubjects()
        Subjects.put(subjects)
        val lessons = lessonRepository.getLessons()
        Lessons.put(lessons)
        val states = stateRepository.getStates()
        States.put(states)
        val students = studentRepository.getStudents()
        Students.put(students)
    }
}