package com.stasenkots.studentstimetable.ui.timetable.dialogs.timepicker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimePickerViewModel : ViewModel() {

    private val _time = MutableLiveData<Pair<Int, Int>>()
    private var _viewId=0
    val viewId
        get() = _viewId
    val time:LiveData<Pair<Int, Int>>
        get() = _time
    fun setTime(hour: Int, minute: Int,viewId:Int) {
        _time.postValue(Pair(hour, minute))
        _viewId=viewId
    }
}