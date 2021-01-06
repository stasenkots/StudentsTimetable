package com.stasenkots.logic.domain.subject

import com.stasenkots.logic.db.dao.SubjectDao
import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.subject.Subject
import com.stasenkots.logic.repository.subject.SubjectRepository
import javax.inject.Inject

class SaveSubjectsToDbUseCase {

    @Inject
    lateinit var subjectRepository: SubjectRepository

    init {
        DaggerLogicComponent
            .create()
            .initializeSaveSubjectsToDbUseCase(this)
    }

    suspend fun doWork(params:Params) {
        subjectRepository.saveSubjectsToDb(params.dao,params.list)
    }
    data class Params(val dao: SubjectDao,val list: List<Subject>)
}