package com.stasenkots.logic.domain.subject

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.subject.Subject
import com.stasenkots.logic.entity.subject.Subjects
import com.stasenkots.logic.repository.subject.SubjectRepository
import com.stasenkots.logic.utils.launchAsync
import javax.inject.Inject

class LoadSubjectsUseCase {

    @Inject
    lateinit var subjectRepository: SubjectRepository

    init {
        DaggerLogicComponent
            .create()
            .initializeLoadSubjectsUseCase(this)
    }

    suspend fun doWork(): List<Subject> {
        val subjects = subjectRepository.getSubjects()
        subjectRepository.setLiveQuery()
        return subjects

    }
}