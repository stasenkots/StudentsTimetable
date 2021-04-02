package com.stasenkots.studentstimetable.ui.timetable.dialogs.detele_dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stasenkots.logic.domain.lesson.DeleteLessonUseCase
import com.stasenkots.logic.utils.launchIO
import com.stasenkots.studentstimetable.Analytics
import timber.log.Timber

class DeleteDialogViewModel(private val analytics: Analytics) : ViewModel() {
    private val deleteLessonUseCase = DeleteLessonUseCase()
    private val _status = MutableLiveData<Exception?>()
    val status: LiveData<Exception?>
        get() = _status

    fun deleteLesson(id: String) {
        launchIO {
            try {
                deleteLessonUseCase.doWork(DeleteLessonUseCase.Params(id))
                _status.postValue(null)
            } catch (e:Exception) {
                analytics.logError(e)
                Timber.e(e)
                _status.postValue(e)
            }
        }
    }
}