package com.stasenkots.logic.domain.student

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.state.State
import com.stasenkots.logic.entity.student.Student
import com.stasenkots.logic.entity.student.Students
import com.stasenkots.logic.repository.student.StudentRepository
import javax.inject.Inject

class LoadStudentsUseCase {
    @Inject
    lateinit var studentRepository: StudentRepository

    init {
        DaggerLogicComponent
            .create()
            .initializeGetStudentsUseCase(this)
    }

    suspend fun doWork():List<Student>{
        return studentRepository.getStudents()
    }
}