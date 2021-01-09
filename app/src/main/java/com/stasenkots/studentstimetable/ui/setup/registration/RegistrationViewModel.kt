package com.stasenkots.studentstimetable.ui.setup.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stasenkots.logic.domain.group.HasAnyGroupWithUseCase
import com.stasenkots.logic.domain.user.SaveUserUseCase
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.utils.launchIO


class RegistrationViewModel : ViewModel(){
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
                e.printStackTrace()
                _errorBus.postValue(e)
            }
        }
    }
}