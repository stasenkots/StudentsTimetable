package com.stasenkots.logic.entity.state

import androidx.lifecycle.MutableLiveData
import com.stasenkots.logic.entity.subject.Subject
import com.stasenkots.logic.entity.subject.Subjects

object States {
    internal val modifiedObject= MutableLiveData<State>()
    internal val createdObject= MutableLiveData<State>()
    internal val deletedObject= MutableLiveData<String>()
    internal val map = mutableMapOf<String, State>()
    fun get(): Map<String, State> {
        return map
    }
    fun put(states:List<State>){
        for (state in states) {
            map[state.id]=state
        }
    }
}