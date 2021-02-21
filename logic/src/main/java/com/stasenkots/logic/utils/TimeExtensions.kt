package com.stasenkots.logic.utils

import com.stasenkots.logic.entity.Group
import com.stasenkots.logic.entity.DayOfWeek
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*


fun String.toDate(pattern: String = "yyyyMMdd"): LocalDate {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return LocalDate.parse(this, formatter)
}

fun String.toLong(pattern: String = ISO_PATTERN): Long {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.parse(this)?.time ?: LocalDate.now().toLong()
}

fun LocalDate.parseToString(pattern: String = "dd MMMM(EE)"): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return this.format(formatter)
}

fun Pair<Int, Int>.parseAsTime() = String.format("%02d:%02d", first, second)

fun Int.toOddEvenWeek(): Int {
    return this % 2
}

fun Int.toCalendarWeek(): Int {
    return if (this == 0) 1 else 2
}

fun String.parseTime(): Pair<Int, Int> {
    val hours = this.substringBefore(":").toInt()
    val minutes = this.substringAfter(":").toInt()
    return Pair(hours, minutes)
}

fun LocalDate.convertToDayOfWeek(): DayOfWeek {
    val day = dayOfWeek.value
    val currentWeek = week()
    val semStartWeek = Group.semStartDate.week()
    var diffWeeks = currentWeek - semStartWeek - 1
    if (diffWeeks < 0) {
        diffWeeks += getNumberOfWeeksInYear(Group.semStartDate.year)
    }
    return DayOfWeek(
        dayOfWeek = day,
        week = diffWeeks.toOddEvenWeek()
    )
}

fun getNumberOfWeeksInYear(year: Int): Int {
    val is53weekYear = LocalDate.of(year, 1, 1).dayOfWeek == java.time.DayOfWeek.THURSDAY ||
            LocalDate.of(year, 12, 31).dayOfWeek == java.time.DayOfWeek.THURSDAY
    return if (is53weekYear) 53 else 52
}

fun LocalDate.week(): Int {
    val weekFields = WeekFields.of(Locale.getDefault())
    return get(weekFields.weekOfWeekBasedYear())
}

fun LocalDate.toLong(): Long {
    return atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun Long.toLocalDate(): LocalDate =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()

