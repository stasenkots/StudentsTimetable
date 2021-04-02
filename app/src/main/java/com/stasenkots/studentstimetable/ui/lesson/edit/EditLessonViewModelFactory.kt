package com.stasenkots.studentstimetable.ui.lesson.edit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stasenkots.studentstimetable.Analytics
import com.stasenkots.studentstimetable.ui.homework.HomeworkViewModel

class EditLessonViewModelFactory(private val application: Application) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditLessonViewModel(Analytics(application)) as T
    }
}