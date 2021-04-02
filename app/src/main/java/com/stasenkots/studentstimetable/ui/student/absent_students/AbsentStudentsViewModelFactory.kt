package com.stasenkots.studentstimetable.ui.student.absent_students

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stasenkots.studentstimetable.Analytics

class AbsentStudentsViewModelFactory(private val application: Application) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AbsentStudentsViewModel(Analytics(application)) as T
    }
}