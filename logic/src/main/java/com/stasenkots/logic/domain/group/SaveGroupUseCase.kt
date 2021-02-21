package com.stasenkots.logic.domain.group

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.repository.group.GroupRepository
import java.time.LocalDate
import javax.inject.Inject

class SaveGroupUseCase {
    @Inject
    lateinit var groupRepository: GroupRepository
    init {
        DaggerLogicComponent
            .create()
            .initializeSaveGroupUseCase(this)
    }
    suspend fun doWork(params: Params){
        groupRepository.saveGroup(params.groupId,params.date)
    }
    data class Params(val groupId:String,val date:LocalDate)
}