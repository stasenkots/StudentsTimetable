package com.stasenkots.logic.entity

import java.time.LocalDate

class Group(
    var groupId: String = "",
    var semStartDate: LocalDate = LocalDate.now()
) {
    companion object {
         val mGroup = Group()
    }
}