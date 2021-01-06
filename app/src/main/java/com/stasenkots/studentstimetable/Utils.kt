package com.stasenkots.studentstimetable

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.stasenkots.logic.entity.DayOfWeek
import com.stasenkots.logic.utils.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.format.TextStyle
import java.util.*


fun Activity.showError(
    snackBar: View,
    error: String = getString(R.string.no_internet_connection),
) {
    Snackbar.make(snackBar, error, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.showError(
    snackBar: View,
    action: View.OnClickListener,
    error: String = getString(R.string.no_internet_connection),
    actionLabel: String = getString(R.string.retry)
) {
    Snackbar.make(snackBar, error, Snackbar.LENGTH_INDEFINITE)
        .setAction(actionLabel, action)
        .show()
}

fun DayOfWeek.convertToString(context: Context): String {
    val dayOfWeek = java.time.DayOfWeek.of(dayOfWeek)
    val weekString = if (week == -1) String.format(
        context.getString(R.string.brakets),
        context.getString(R.string.every_week),
        dayOfWeek.name

    )
    else String.format(context.getString(R.string.n_week), week.toCalendarWeek())
    return dayOfWeek.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()) + weekString
}


fun String.toDayOfWeek(context: Context): DayOfWeek {
    val formatter = SimpleDateFormat("EEEE", Locale.getDefault())
    val date =
        formatter.parse(substringBefore("("))?.time?.toLocalDate()
            ?: throw Exception("Invalid format date")
    val dayInt = date.dayOfWeek.value
    val week = this.substringAfter('(').substringBefore(')')
    val weekInt = if (week == context.getString(R.string.every_week))
        EVERY_WEEK_INT
    else
        Integer.parseInt(week[0].toString()).toOddEvenWeek()

    return DayOfWeek(dayInt, weekInt)
}

fun Pair<Int, Int>.parseAsTime() = String.format("%02d:%02d", first, second)

