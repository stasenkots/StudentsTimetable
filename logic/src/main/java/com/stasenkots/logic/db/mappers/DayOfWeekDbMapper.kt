package com.stasenkots.logic.db.mappers


import com.stasenkots.logic.entity.DayOfWeek
import com.stasenkots.logic.network.dto.DayResponse
import com.stasenkots.logic.utils.EVERY_WEEK_INT

import org.json.JSONObject
import java.util.*
import javax.inject.Inject


class DayOfWeekDbMapper @Inject constructor() {

    fun map(dayOfWeek: DayOfWeek) = JSONObject(
        mapOf(
            "dayOfWeek" to dayOfWeek.dayOfWeek,
            "week" to dayOfWeek.week
        )
    )

    fun map(from: JSONObject?) = DayOfWeek(
        dayOfWeek = from?.getInt("dayOfWeek") ?: Calendar.SUNDAY,
        week = from?.getInt("week") ?: EVERY_WEEK_INT
    )

}