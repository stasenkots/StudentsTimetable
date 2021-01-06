package com.stasenkots.logic.domain.states

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.state.State
import com.stasenkots.logic.entity.state.States
import com.stasenkots.logic.repository.states.StatesRepository
import com.stasenkots.logic.utils.launchAsync
import javax.inject.Inject

class LoadStatesUseCase {
    @Inject
    lateinit var stateRepository: StatesRepository

    init {
        DaggerLogicComponent
            .create()
            .initializeLoadStatesUseCase(this)
    }

    suspend fun doWork():List<State> {
        val states = stateRepository.getStates()
        stateRepository.setLiveQuery()
        return states
    }
}