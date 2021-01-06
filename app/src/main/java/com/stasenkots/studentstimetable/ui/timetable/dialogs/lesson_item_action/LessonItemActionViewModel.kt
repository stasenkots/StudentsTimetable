package com.stasenkots.studentstimetable.ui.timetable.dialogs.lesson_item_action

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stasenkots.studentstimetable.*


class LessonItemActionViewModel : ViewModel() {

    private val _lessonItemAction = MutableLiveData<String>()
    private var _lessonId: String?=null
    val lessonId
        get() = _lessonId
    private var _date: Long?=0
    val date
    get() = _date
    val lessonItemAction: LiveData<String>
        get() = _lessonItemAction

    fun setLessonItemAction(actionId: Int,lessonId: String?=null, date: Long?=null) {
        this._date = date
        this._lessonId = lessonId
        _lessonItemAction.postValue(activitiesActions[actionId])

    }
}