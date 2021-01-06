package com.stasenkots.logic.domain.states

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.repository.states.StatesRepository
import javax.inject.Inject

class DeleteStateUseCase {
    @Inject
    lateinit var stateRepository: StatesRepository

    init {
        DaggerLogicComponent
            .create()
            .initializeDeleteStateUseCase(this)
    }

    suspend fun doWork(params: Params) {
        stateRepository.deleteState(params.stateId)
    }
    data class Params(
        val stateId:String
    )
}