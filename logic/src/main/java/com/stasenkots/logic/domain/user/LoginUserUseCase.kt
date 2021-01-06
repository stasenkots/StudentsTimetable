package com.stasenkots.logic.domain.user

import android.content.Intent
import android.content.SharedPreferences
import com.parse.Parse
import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.repository.user.UserRepository
import javax.inject.Inject

class LoginUserUseCase {
    @Inject
    lateinit var userRepository:UserRepository
    init {
        DaggerLogicComponent
            .create()
            .initializeLoginUserUseCase(this)

    }
    suspend fun doWork(params: Params){
        userRepository.loginUser(params.data)
    }
    data class Params(val data: Intent)
}