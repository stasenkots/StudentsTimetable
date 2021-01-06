package com.stasenkots.logic.domain.user

import android.content.SharedPreferences
import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.repository.user.UserRepository
import javax.inject.Inject

class SaveUserUseCase {
    @Inject
    lateinit var userRepository: UserRepository
    init {
        DaggerLogicComponent
            .create()
            .initializeSaveUserUseCase(this)

    }
    fun doWork(){
        userRepository.saveUserLoginData()
    }
}