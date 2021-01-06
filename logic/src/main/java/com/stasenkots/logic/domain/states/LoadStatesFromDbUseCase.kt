package com.stasenkots.logic.domain.states

import com.stasenkots.logic.db.dao.StateDao
import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.state.State
import com.stasenkots.logic.entity.state.States
import com.stasenkots.logic.repository.states.StatesRepository
import com.stasenkots.logic.utils.launchAsync
import javax.inject.Inject

class LoadStatesFromDbUseCase {
    @Inject
    lateinit var stateRepository: StatesRepository

    init {
        DaggerLogicComponent
            .create()
            .initializeLoadStatesFromDbUseCase(this)
    }

    suspend fun doWork(params: Params):List<State> {
        return stateRepository.getStateFromDb(params.dao)

    }
    data class Params(val dao: StateDao)
}