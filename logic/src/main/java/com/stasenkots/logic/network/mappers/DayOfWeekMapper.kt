package com.stasenkots.logic.network.mappers


import com.stasenkots.logic.entity.DayOfWeek
import com.stasenkots.logic.network.dto.DayResponse
import com.stasenkots.logic.utils.EVERY_WEEK_INT

import org.json.JSONObject
import java.util.*
import javax.inject.Inject


class DayOfWeekMapper @Inject constructor() {

    fun map(dayResponse: DayResponse?) = DayOfWeek(
        dayOfWeek = dayResponse?.day?:Calendar.SUNDAY,
        week = dayResponse?.week ?: EVERY_WEEK_INT
    )
    fun map(dayOfWeek: DayOfWeek) = DayResponse(
        day = dayOfWeek.dayOfWeek,
        week = dayOfWeek.week
    )
    fun map(from: JSONObject?)=DayOfWeek(
        dayOfWeek = from?.getInt("day")?:Calendar.SUNDAY,
        week = from?.getInt("week")?: EVERY_WEEK_INT
    )

}