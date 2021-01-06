package com.stasenkots.logic.domain.group

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.repository.group.GroupRepository
import javax.inject.Inject

class SaveGroupUseCase {
    @Inject
    lateinit var groupRepository: GroupRepository
    init {
        DaggerLogicComponent
            .create()
            .initializeSaveGroupUseCase(this)
    }
    fun doWork(){
        groupRepository.saveGroup()
    }
}