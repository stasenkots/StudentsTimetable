package com.stasenkots.studentstimetable.ui.homework.archive

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stasenkots.logic.entity.state.State
import com.stasenkots.logic.entity.state.States

class HomeworksArchiveViewModel : ViewModel() {
    private lateinit var _states: List<State>
    val states
        get() = _states

    fun getStates(subjectId: String) {
        _states= States.get().values.filter { it.subject == subjectId }.toList()
            .sortedByDescending { it.date }

    }

}