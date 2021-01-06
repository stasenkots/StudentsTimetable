package com.stasenkots.logic.entity.state

import java.time.LocalDate

data class State(
    val id:String="",
    val subject:String="",
    var date: LocalDate=LocalDate.now(),
    var homework:String="",
    var comment:String="",
    var absentUsers:List<String>
)
