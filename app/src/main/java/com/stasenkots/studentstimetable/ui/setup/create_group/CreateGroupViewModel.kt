package com.stasenkots.studentstimetable.ui.setup.create_group


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stasenkots.logic.domain.group.SaveGroupUseCase
import com.stasenkots.logic.domain.user.SaveUserUseCase
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.utils.launchIO
import java.util.*

class CreateGroupViewModel : ViewModel() {
    private val _isSaved = MutableLiveData<Unit>()
    val isSaved: LiveData<Unit>
        get() = _isSaved
    private val _errorBus = MutableLiveData<Exception>()
    val errorBus: LiveData<Exception>
        get() = _errorBus
    private val saveUserUseCase = SaveUserUseCase()
    private val saveGroupUseCase = SaveGroupUseCase()

    fun setGroupId() {
        User.groupId = UUID.randomUUID().toString().replace("-", "")
    }

    fun save() {
        launchIO {
            try {
                saveUserUseCase.doWork()
                saveGroupUseCase.doWork()
                _isSaved.postValue(Unit)
            } catch (e: Exception) {
                _errorBus.postValue(e)
            }

        }
    }
}

