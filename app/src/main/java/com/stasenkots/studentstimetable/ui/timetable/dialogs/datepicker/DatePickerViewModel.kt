package com.stasenkots.studentstimetable.ui.timetable.dialogs.datepicker

import android.icu.util.LocaleData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.util.*

class DatePickerViewModel:ViewModel() {
    private val _currentDate = MutableLiveData(LocalDate.now())
    val currentDate:LiveData<LocalDate>
        get() = _currentDate

    fun setCurrentDate(date: LocalDate) {
        _currentDate.postValue(date)
    }
}