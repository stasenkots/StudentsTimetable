package com.stasenkots.studentstimetable.ui.lesson.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stasenkots.logic.domain.lesson_item.GetLessonItemUseCase
import com.stasenkots.logic.domain.lesson_item.SendLessonItemUseCase
import com.stasenkots.logic.entity.LessonItem
import com.stasenkots.logic.entity.subject.Subjects
import com.stasenkots.logic.utils.launchIO
import java.time.LocalDate

class EditLessonViewModel : ViewModel() {
    private val getLessonItemUseCase = GetLessonItemUseCase()
    private val sendLessonItemUseCase = SendLessonItemUseCase()
    val subjectNames by lazy { Subjects.get().values.map { it.name }.distinct() }
    val subjectTeachers by lazy { Subjects.get().values.map { it.teacher }.distinct() }
    val subjectSubgroups by lazy { Subjects.get().values.map { it.subgroup }.distinct() }
    val subjectType by lazy { Subjects.get().values.map { it.type }.distinct() }
    lateinit var lessonItem: LessonItem
    private val _status = MutableLiveData<Throwable?>()
    val status: LiveData<Throwable?>
        get() = _status

    fun getLessonItem(lessonId: String?, date: LocalDate = LocalDate.now()): LessonItem {
        lessonItem = getLessonItemUseCase.doWork(GetLessonItemUseCase.Params(lessonId, date))
        return lessonItem
    }

    fun sendData(lessonItem: LessonItem) {
        val params = SendLessonItemUseCase.Params(lessonItem)
        launchIO {
            try {
                sendLessonItemUseCase.doWork(params)
                _status.postValue(null)
            } catch (e: Throwable) {
                _status.postValue(e)
            }

        }
    }
}