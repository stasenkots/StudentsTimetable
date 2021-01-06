package com.stasenkots.logic.entity

import android.icu.util.LocaleData
import com.stasenkots.logic.utils.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

data class DayOfWeek(
    val dayOfWeek: Int = LocalDate.now().dayOfWeek.value,
    var week: Int = EVERY_WEEK_INT
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DayOfWeek

        if (dayOfWeek != other.dayOfWeek) return false
        if (week != other.week && week != EVERY_WEEK_INT && other.week != EVERY_WEEK_INT) return false

        return true
    }

    override fun hashCode(): Int {
        return dayOfWeek
    }

    operator fun minus(rhs: DayOfWeek): Int {
        var days = dayOfWeek - rhs.dayOfWeek
        if ((week == FIRST_WEEK && rhs.week == SECOND_WEEK)
            || (week == SECOND_WEEK && rhs.week == FIRST_WEEK)){
            days+=(week- rhs.week)*Calendar.DAY_OF_WEEK
        }
        return days
    }


}