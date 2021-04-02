package com.stasenkots.studentstimetable.ui.homework.edit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stasenkots.studentstimetable.Analytics

class EditHomeworkViewModelFactory(private val application: Application) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditHomeworkViewModel(Analytics(application)) as T
    }
}