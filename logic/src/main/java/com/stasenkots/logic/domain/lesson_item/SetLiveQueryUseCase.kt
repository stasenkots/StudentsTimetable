package com.stasenkots.logic.domain.lesson_item

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stasenkots.logic.db.dao.LessonDao
import com.stasenkots.logic.db.dao.StateDao
import com.stasenkots.logic.db.dao.SubjectDao
import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.LessonItem
import com.stasenkots.logic.repository.lesson_item.LessonItemRepository
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class SetLiveQueryUseCase {
    @Inject
    lateinit var lessonItemRepository: LessonItemRepository


    init {
        DaggerLogicComponent
            .create()
            .initializeSetLiveQueryUseCase(this)
    }

    fun doWork(params: Params) :LiveData<Unit>{
        return lessonItemRepository.setLiveQuery(
            params.lessonDao,
            params.subjectDao,
            params.stateDao
        )
    }

    data class Params(
        val lessonDao: LessonDao,
        val subjectDao: SubjectDao,
        val stateDao: StateDao
    )
}