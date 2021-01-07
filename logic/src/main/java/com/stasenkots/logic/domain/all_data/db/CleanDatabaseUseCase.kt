package com.stasenkots.logic.domain.all_data.db

import com.stasenkots.logic.di.components.DaggerLogicComponent

class CleanDatabaseUseCase : DatabaseUseCase() {
    init {
        DaggerLogicComponent
            .create()
            .initializeCleanDatabaseUseCase(this)
    }

    override suspend fun doWork(params: Params) {
        lessonRepository.clearDb(params.lessonDao)
        subjectRepository.cleanDb(params.subjectDao)
        stateRepository.cleanDb(params.stateDao)
        studentRepository.cleanDb(params.studentDao)

    }
}