package com.stasenkots.logic.domain.student

import com.stasenkots.logic.db.dao.StudentDao
import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.state.State
import com.stasenkots.logic.entity.student.Student
import com.stasenkots.logic.entity.student.Students
import com.stasenkots.logic.repository.student.StudentRepository
import javax.inject.Inject

class SaveStudentsToDbUseCase {
    @Inject
    lateinit var studentRepository: StudentRepository

    init {
        DaggerLogicComponent
            .create()
            .initializeSaveStudentsToDbUseCase(this)
    }

    suspend fun doWork(params:Params){
         studentRepository.saveStudentsToDb(params.dao,params.list)
    }
    data class Params(val dao:StudentDao,val list: List<Student>)
}