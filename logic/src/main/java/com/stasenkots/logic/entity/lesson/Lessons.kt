package com.stasenkots.logic.entity.lesson

import androidx.lifecycle.MutableLiveData
import com.stasenkots.logic.entity.subject.Subject
import com.stasenkots.logic.entity.subject.Subjects

object Lessons {
    internal var map = mutableMapOf<String, Lesson>()
    internal val modifiedObject=MutableLiveData<Lesson>()
    internal val createdObject=MutableLiveData<Lesson>()
    internal val deletedObject=MutableLiveData<String>()
    fun get(): Map<String, Lesson> {
        return map
    }
    fun put(lessons:List<Lesson>){
        map=lessons.associateBy { it.id }.toMutableMap()
    }
}