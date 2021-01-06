package com.stasenkots.studentstimetable.ui.homework.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stasenkots.logic.entity.lesson.Lessons
import com.stasenkots.logic.utils.EVERY_WEEK_INT
import com.stasenkots.logic.utils.toLocalDate
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.*

class HomeworkDatesViewModel : ViewModel() {
    private lateinit var items: List<LocalDate>
    private val _day = MutableLiveData<LocalDate>()
    val day: LiveData<LocalDate>
        get() = _day

    fun init(dates: List<LocalDate>) {
        items = dates
    }

    fun setDates(checkedItem: Int) {
        _day.postValue(items[checkedItem])
    }

    fun getDates(subjectId: String, date: Long = 0): List<LocalDate> {
        val daysOfWeek = Lessons.get().values.filter { lesson ->
            lesson.subject == subjectId
        }.map {
            it.dayOfWeek
        }
        val dates = mutableListOf<LocalDate>()
        val currentDate=date.toLocalDate()

        for (dayOfWeek in daysOfWeek) {
            val period = if (dayOfWeek.week == EVERY_WEEK_INT) 1 else 2
            val day=DayOfWeek.of(dayOfWeek.dayOfWeek)
            var mDate=currentDate.with(TemporalAdjusters.nextOrSame(day))
            for (i in 1..2) {
                dates.add(mDate)
                mDate=mDate.plusWeeks(period.toLong())
            }
        }
        return dates.sortedBy { it }
    }
}