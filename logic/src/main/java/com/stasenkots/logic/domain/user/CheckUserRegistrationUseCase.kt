package com.stasenkots.logic.domain.user

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.repository.user.UserRepository
import javax.inject.Inject

class CheckUserRegistrationUseCase {
    @Inject
    lateinit var userRepository: UserRepository
    init {
        DaggerLogicComponent
            .create()
            .initializeCheckUserRegistrationUseCase(this)

    }
    suspend fun doWork():Boolean{
        return userRepository.isUserRegistered()
    }
}