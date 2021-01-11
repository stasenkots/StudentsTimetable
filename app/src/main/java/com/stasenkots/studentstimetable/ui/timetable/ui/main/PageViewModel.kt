package com.stasenkots.studentstimetable.ui.timetable.ui.main

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.stasenkots.logic.db.database.LessonDatabaseProvider
import com.stasenkots.logic.db.database.StateDatabaseProvider
import com.stasenkots.logic.db.database.SubjectDatabaseProvider
import com.stasenkots.logic.domain.lesson_item.GetLessonItemsUseCase
import com.stasenkots.logic.domain.lesson_item.SetLiveQueryUseCase

import com.stasenkots.logic.entity.LessonItem
import java.time.LocalDate
import java.util.*

class PageViewModel(app: Application) : AndroidViewModel(app) {

    private var _index = 0
    private lateinit var _currentDate: LocalDate
    private val _lessons = MutableLiveData<MutableList<LessonItem>>()
    private val getLessonItemsUseCase = GetLessonItemsUseCase()
    private val setLiveQueryUseCase = SetLiveQueryUseCase()
    var hasLiveQuery=false
    val lessons: LiveData<MutableList<LessonItem>>
        get() = _lessons
    private val lessonDao = LessonDatabaseProvider.provide(app.applicationContext).getDao()
    private val subjectDao = SubjectDatabaseProvider.provide(app.applicationContext).getDao()
    private val stateDao = StateDatabaseProvider.provide(app.applicationContext).getDao()

    fun setIndex(index: Int) {
        _index = index
    }

    fun setDate(currentDate: LocalDate) {
        val additionalDays: Long =
            _index.toLong() - currentDate.dayOfWeek.value
        _currentDate = currentDate.plusDays(additionalDays)
        getLessons()
    }

    fun getLessons() {
        val list = getLessonItemsUseCase.doWork(GetLessonItemsUseCase.Params(_currentDate))
            .sortedBy { it.timeStart }.toMutableList()
        _lessons.postValue(list)
    }


    fun setLiveQuery(lifecycleOwner: LifecycleOwner) {
        setLiveQueryUseCase.doWork(
            SetLiveQueryUseCase.Params(
                lessonDao,
                subjectDao,
                stateDao
            )
        ).observe(lifecycleOwner, Observer{
            getLessons()
        })
        hasLiveQuery=true
    }

}