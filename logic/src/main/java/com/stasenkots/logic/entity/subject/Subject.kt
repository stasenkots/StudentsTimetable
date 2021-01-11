package com.stasenkots.logic.entity.subject

import com.stasenkots.logic.utils.toLong
import java.time.LocalDate

data class Subject(
    var id:String="",
    val subgroup: String="",
    val teacher: String="",
    val updatedAt:Long= LocalDate.now().toLong(),
    val name:String="",
    val type:String="",
)