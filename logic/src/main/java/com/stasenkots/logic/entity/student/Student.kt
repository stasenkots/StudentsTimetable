package com.stasenkots.logic.entity.student

import com.stasenkots.logic.utils.toLong
import java.time.LocalDate

data class Student(
    val name: String,
    val updatedAt:Long= LocalDate.now().toLong(),
    val id: String
)