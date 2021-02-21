package com.stasenkots.studentstimetable.ui.setup.start_of_sem_picker

import androidx.lifecycle.ViewModel
import com.stasenkots.logic.entity.Group
import java.time.LocalDate

class SemStartViewModel : ViewModel() {
    fun setStartOfSemesters(date:LocalDate){
        Group.semStartDate= date
    }
}