package com.stasenkots.logic.entity.lesson

import com.stasenkots.logic.entity.DayOfWeek
import com.stasenkots.logic.utils.toLong
import java.time.LocalDate

data class Lesson(
    val dayOfWeek: DayOfWeek = DayOfWeek(),
    var subject: String="",
    val id: String="",
    val updatedAt:Long=LocalDate.now().toLong(),
    val room: String="",
    val timeEnd: String="",
    val timeStart: String=""
)
