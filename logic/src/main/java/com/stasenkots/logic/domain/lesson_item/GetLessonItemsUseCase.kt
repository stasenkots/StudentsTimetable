package com.stasenkots.logic.domain.lesson_item

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.LessonItem
import com.stasenkots.logic.repository.lesson_item.LessonItemRepository
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class GetLessonItemsUseCase {
    @Inject
    lateinit var lessonItemRepository:LessonItemRepository

    init {
        DaggerLogicComponent
            .create()
            .initializeGetLessonItemsUseCase(this)
    }

    fun doWork(params:Params):List<LessonItem>{
        return lessonItemRepository.getLessonItems(params.currentDate)
    }
    data class Params(
        val currentDate: LocalDate
    )


}