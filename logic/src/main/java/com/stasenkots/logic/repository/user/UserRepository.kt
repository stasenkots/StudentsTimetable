package com.stasenkots.logic.repository.user

import android.content.Intent
import com.parse.ParseUser
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDataSource: UserDataSource) {
   suspend fun loginUser(data:Intent) {
        userDataSource.loginUser(data)
    }

    fun saveUserLoginData() {
        userDataSource.saveData()
    }
    suspend fun isUserRegistered():Boolean{
        return userDataSource.isUserRegistered()
    }
}