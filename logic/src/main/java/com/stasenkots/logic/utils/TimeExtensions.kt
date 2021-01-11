package com.stasenkots.logic.utils

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
fun String.toLong(pattern: String= ISO_PATTERN):Long{
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.parse(this)?.time?:LocalDate.now().toLong()
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
    return if (this == 0) 2 else 1
}

fun String.parseTime(): Pair<Int, Int> {
    val hours = this.substringBefore(":").toInt()
    val minutes = this.substringAfter(":").toInt()
    return Pair(hours, minutes)
}

fun LocalDate.convertToDayOfWeek(): DayOfWeek {
    val day = dayOfWeek.value
    val weekFields: WeekFields = WeekFields.of(Locale.getDefault())
    val week: Int = get(weekFields.weekOfWeekBasedYear())
    return DayOfWeek(
        dayOfWeek = day,
        week = week.toOddEvenWeek()
    )
}

fun LocalDate.toLong(): Long {
    return atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun Long.toLocalDate(): LocalDate =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()

