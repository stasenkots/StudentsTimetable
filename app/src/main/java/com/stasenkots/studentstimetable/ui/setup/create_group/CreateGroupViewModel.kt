package com.stasenkots.studentstimetable.ui.setup.create_group


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stasenkots.logic.domain.group.SaveGroupUseCase
import com.stasenkots.logic.domain.user.SaveUserUseCase
import com.stasenkots.logic.entity.Group
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.utils.launchIO
import com.stasenkots.studentstimetable.Analytics
import timber.log.Timber
import java.util.*

class CreateGroupViewModel(private val analytics: Analytics) : ViewModel() {
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
        Group.mGroup.groupId = User.groupId
    }

    fun save() {
        launchIO {
            try {
                saveUserUseCase.doWork()
                saveGroupUseCase.doWork(SaveGroupUseCase.Params(User.groupId, Group.mGroup.semStartDate))
                _isSaved.postValue(Unit)
            } catch (e: Exception) {
                analytics.logError(e)
                _errorBus.postValue(e)
                Timber.e(e)
            }

        }
    }
}

