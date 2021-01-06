package com.stasenkots.logic.entity.lesson

import com.stasenkots.logic.entity.DayOfWeek

data class Lesson(
    val dayOfWeek: DayOfWeek = DayOfWeek(),
    var subject: String="",
    val id: String="",
    val room: String="",
    val timeEnd: String="",
    val timeStart: String=""
)
