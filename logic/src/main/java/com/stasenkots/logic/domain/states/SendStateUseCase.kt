package com.stasenkots.logic.domain.states

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.state.State
import com.stasenkots.logic.repository.states.StatesRepository
import javax.inject.Inject

class SendStateUseCase {
    @Inject
    lateinit var stateRepository: StatesRepository

    init {
        DaggerLogicComponent
            .create()
            .initializeSendStatesUseCase(this)
    }

    suspend fun doWork(params: Params) {
        stateRepository.sendState(params.state)
    }
    data class Params(
        val state: State
    )
}