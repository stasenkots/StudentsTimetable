package com.stasenkots.logic.domain.group

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.Group
import com.stasenkots.logic.repository.group.GroupRepository
import javax.inject.Inject

class GetGroupUseCase {
    @Inject
    lateinit var groupRepository: GroupRepository

    init {
        DaggerLogicComponent
            .create()
            .initializeGetGroupUseCase(this)
    }

    suspend fun doWork(params: Params): Group {
        return groupRepository.getGroup(params.id)
    }

    data class Params(
        val id: String
    )
}