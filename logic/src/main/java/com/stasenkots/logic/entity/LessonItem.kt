package com.stasenkots.logic.entity

import com.stasenkots.logic.entity.state.State
import java.time.LocalDate

data class LessonItem(
    var name: String = "",
    var type: String = "",
    val lesson: String = "",
    val subject: String = "",
    val timeStart: String = "",
    val timeEnd: String = "",
    val room: String = "",
    var date: LocalDate= LocalDate.now(),
    val dayOfWeek: DayOfWeek=DayOfWeek(),
    var subgroup: String = "",
    var teacher: String = "",
    var state: State?=null
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LessonItem

        if (lesson != other.lesson) return false

        return true
    }

    override fun hashCode(): Int {
        return lesson.hashCode()
    }
}