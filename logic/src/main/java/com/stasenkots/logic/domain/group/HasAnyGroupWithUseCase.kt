package com.stasenkots.logic.domain.group

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.repository.group.GroupRepository
import javax.inject.Inject

class HasAnyGroupWithUseCase {
    @Inject
    lateinit var groupRepository: GroupRepository
    init {
        DaggerLogicComponent
            .create()
            .initializeHasAnyGroupWithIdUseCase(this)
    }
     fun doWork(params:Params):Boolean{
        return groupRepository.hasAnyGroupWithID(params.id)
    }
    data class Params(
        val id: String
    )
}