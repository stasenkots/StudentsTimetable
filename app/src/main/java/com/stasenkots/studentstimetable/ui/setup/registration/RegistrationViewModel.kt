package com.stasenkots.studentstimetable.ui.setup.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stasenkots.logic.domain.group.HasAnyGroupWithUseCase
import com.stasenkots.logic.domain.user.SaveUserUseCase
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.utils.launchIO
import com.stasenkots.studentstimetable.Analytics
import timber.log.Timber


class RegistrationViewModel(private val analytics: Analytics) : ViewModel(){
    private val _isGroupExist = MutableLiveData<Boolean>()
    val isGroupExist: LiveData<Boolean>
        get() = _isGroupExist
    private val _errorBus = MutableLiveData<Exception>()
    val errorBus: LiveData<Exception>
        get() = _errorBus
    private val hasAnyGroupWithUseCase=HasAnyGroupWithUseCase()
    private val saveUserUseCase=SaveUserUseCase()
    fun checkGroupExistence(id: String) {
        launchIO {
            try {
                val params=HasAnyGroupWithUseCase.Params(id)
                _isGroupExist.postValue(hasAnyGroupWithUseCase.doWork(params))
            }catch (e:Exception){
                _errorBus.postValue(e)
                Timber.e(e)
                analytics.logError(e)
            }
        }
    }

    fun saveUser(groupId: String) {
        User.groupId=groupId
        launchIO {
            try {
                saveUserUseCase.doWork()
                _errorBus.postValue(null)
            }catch (e:Exception){
                analytics.logError(e)
                Timber.e(e)
                _errorBus.postValue(e)
            }
        }
    }
}