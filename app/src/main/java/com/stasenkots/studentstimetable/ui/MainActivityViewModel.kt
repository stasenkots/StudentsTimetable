package com.stasenkots.studentstimetable.ui

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stasenkots.logic.db.database.LessonDatabaseProvider
import com.stasenkots.logic.db.database.StateDatabaseProvider
import com.stasenkots.logic.db.database.StudentDatabaseProvider
import com.stasenkots.logic.db.database.SubjectDatabaseProvider
import com.stasenkots.logic.db.manager.DataBaseLoader
import com.stasenkots.logic.domain.user.CheckUserRegistrationUseCase
import com.stasenkots.logic.domain.user.LoginUserUseCase
import com.stasenkots.logic.utils.TAG
import com.stasenkots.logic.utils.launchIO


class MainActivityViewModel(val app: Application) : AndroidViewModel(app) {
    private val _isDataLoaded = MutableLiveData<Boolean>()
    private val _errorBus = MutableLiveData<Exception>()
    val errorBus: LiveData<Exception>
        get() = _errorBus
    val isDataLoaded: LiveData<Boolean>
        get() = _isDataLoaded
    private val lessonDao = LessonDatabaseProvider.provide(app.applicationContext).getDao()
    private val subjectDao = SubjectDatabaseProvider.provide(app.applicationContext).getDao()
    private val stateDao = StateDatabaseProvider.provide(app.applicationContext).getDao()
    private val studentDao = StudentDatabaseProvider.provide(app.applicationContext).getDao()
    private val loginUserUseCase = LoginUserUseCase()
    private val checkUserRegistrationUseCase = CheckUserRegistrationUseCase()
    private val _isUserRegistered = MutableLiveData<Boolean>()
    val isUserRegistered: LiveData<Boolean>
        get() = _isUserRegistered

    init {
        checkUserRegistration()
    }
    fun loginUser(data: Intent) {
        launchIO {
            try {
                loginUserUseCase.doWork(LoginUserUseCase.Params(data))
            } catch (e: java.lang.Exception) {
                _errorBus.postValue(e)
            }
        }
    }

    private fun checkUserRegistration() {
        _isUserRegistered.postValue(checkUserRegistrationUseCase.doWork())
    }


     fun loadFromDb() {
        launchIO {
            try {
                DataBaseLoader(lessonDao, subjectDao, stateDao, studentDao).doWork()
                _isDataLoaded.postValue(true)
            }catch (e:java.lang.Exception){
                _errorBus.postValue(e)
            }
        }
    }

}