package com.stasenkots.studentstimetable.ui.homework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stasenkots.logic.domain.states.DeleteStateUseCase
import com.stasenkots.logic.utils.launchIO
import java.lang.Exception

const val CONTEXTUAL_MODE = 1
const val NORMAL_MODE = 0

class HomeworkViewModel : ViewModel() {
    val selectedItems = mutableListOf<String>()
    val mode = MutableLiveData<Int>()
    private val _errorBus = MutableLiveData<Exception?>()
    val errorBus: LiveData<Exception?>
        get() = _errorBus
    private val deleteStateUseCase = DeleteStateUseCase()

    fun deleteStates() {
        for (selectedItem in selectedItems) {
            launchIO {
                try {
                    deleteStateUseCase.doWork(DeleteStateUseCase.Params(selectedItem))
                    _errorBus.postValue(null)
                } catch (e: Exception) {
                    e.printStackTrace()
                    _errorBus.postValue(e)
                }
            }
        }
    }
}