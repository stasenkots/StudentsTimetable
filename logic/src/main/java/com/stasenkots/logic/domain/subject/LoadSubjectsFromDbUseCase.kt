package com.stasenkots.logic.domain.subject

import com.stasenkots.logic.db.dao.SubjectDao
import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.subject.Subject
import com.stasenkots.logic.repository.subject.SubjectRepository
import javax.inject.Inject

class LoadSubjectsFromDbUseCase {

    @Inject
    lateinit var subjectRepository: SubjectRepository

    init {
        DaggerLogicComponent
            .create()
            .initializeLoadSubjectsFromDbUseCase(this)
    }

    suspend fun doWork(params:Params): List<Subject> {
        return subjectRepository.getSubjectsFromDb(params.dao)
    }
    data class Params(val dao: SubjectDao)
}