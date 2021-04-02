package com.stasenkots.studentstimetable.ui.homework

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stasenkots.studentstimetable.Analytics

class HomeworkArchiveViewModelFactory(private val application: Application) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeworkViewModel(Analytics(application)) as T
    }
}