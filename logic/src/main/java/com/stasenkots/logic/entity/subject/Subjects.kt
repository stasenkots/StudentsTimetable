package com.stasenkots.logic.entity.subject

import androidx.lifecycle.MutableLiveData

object Subjects {
    internal var map = mutableMapOf<String, Subject>()
    internal val modifiedObject= MutableLiveData<Subject>()
    internal val createdObject= MutableLiveData<Subject>()
    fun get(): Map<String, Subject> {
        return map
    }
    fun put(subjects:List<Subject>){
        for (subject in subjects) {
            map[subject.id] = subject
        }
    }
}