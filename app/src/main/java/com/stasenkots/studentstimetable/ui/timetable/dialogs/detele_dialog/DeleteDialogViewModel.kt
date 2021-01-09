package com.stasenkots.studentstimetable.ui.timetable.dialogs.detele_dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stasenkots.logic.domain.lesson.DeleteLessonUseCase
import com.stasenkots.logic.utils.launchIO

class DeleteDialogViewModel : ViewModel() {
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
                _status.postValue(e)
            }
        }
    }
}