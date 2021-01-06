package com.stasenkots.studentstimetable.ui.timetable.dialogs.day_of_week_picker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DayOfWeekPickerViewModel:ViewModel() {
    private val _dayOfWeek=MutableLiveData<String>()
    val dayOfWeek:LiveData<String>
    get()=_dayOfWeek
     fun setDayOfWeek(value:String){
         _dayOfWeek.postValue(value)
     }
}