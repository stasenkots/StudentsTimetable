package com.stasenkots.studentstimetable.ui.homework.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stasenkots.logic.domain.states.SendStateUseCase
import com.stasenkots.logic.entity.state.State
import com.stasenkots.logic.entity.state.States
import com.stasenkots.logic.utils.*
import com.stasenkots.studentstimetable.Analytics
import timber.log.Timber
import java.lang.Exception
import java.time.LocalDate


class EditHomeworkViewModel(private val analytics: Analytics) : ViewModel() {
    lateinit var date: LocalDate
    lateinit var state: State
    private val sendStateUseCase = SendStateUseCase()
    private val _errorBus = MutableLiveData<Exception?>()
    val errorBus: LiveData<Exception?>
        get() = _errorBus

    fun init(subjectId: String, currentDate: LocalDate) {
        state = States.get().values.find {
            it.date == currentDate &&
                    it.subject == subjectId
        } ?: State(subject = subjectId, date = currentDate, absentUsers = emptyList())
        date=currentDate
    }
    fun reinit(subjectId: String, currentDate: LocalDate) {
        state = States.get().values.find {
            it.date == currentDate &&
                    it.subject == subjectId
        } ?: State(subject = subjectId, date = currentDate, absentUsers = emptyList())
    }

    fun sendState(
        homework: String,
        comment: String,
    ) {
        state.homework = homework
        state.comment = comment
        launchIO {
            try {
                sendStateUseCase.doWork(SendStateUseCase.Params(state))
                _errorBus.postValue(null)
            } catch (e: Exception) {
                analytics.logError(e)
                Timber.e(e)
                _errorBus.postValue(e)
            }
        }

    }


}