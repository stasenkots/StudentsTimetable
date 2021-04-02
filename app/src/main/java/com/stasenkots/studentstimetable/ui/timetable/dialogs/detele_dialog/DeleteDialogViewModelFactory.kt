package com.stasenkots.studentstimetable.ui.timetable.dialogs.detele_dialog

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stasenkots.studentstimetable.Analytics

class DeleteDialogViewModelFactory(private val application: Application) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DeleteDialogViewModel(Analytics(application)) as T
    }
}