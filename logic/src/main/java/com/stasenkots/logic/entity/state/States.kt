package com.stasenkots.logic.entity.state

import androidx.lifecycle.MutableLiveData
import com.stasenkots.logic.entity.lesson.Lesson
import com.stasenkots.logic.entity.subject.Subject
import com.stasenkots.logic.entity.subject.Subjects

object States {
    internal var map = mutableMapOf<String, State>()
    internal val modifiedObject = MutableLiveData<State>()
    internal val createdObject = MutableLiveData<State>()
    internal val deletedObject = MutableLiveData<String>()
    fun get(): Map<String, State> {
        return map
    }

    fun put(states: List<State>) {
        map = states.associateBy { it.id }.toMutableMap()
    }
}