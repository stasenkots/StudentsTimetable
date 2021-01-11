package com.stasenkots.logic.entity.state

import com.stasenkots.logic.utils.toLong
import java.time.LocalDate

data class State(
    val id:String="",
    val subject:String="",
    val updatedAt:Long=LocalDate.now().toLong(),
    var date: LocalDate=LocalDate.now(),
    var homework:String="",
    var comment:String="",
    var absentUsers:List<String>
)
