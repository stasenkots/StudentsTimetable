package com.stasenkots.logic.domain.lesson_item

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.LessonItem
import com.stasenkots.logic.repository.lesson_item.LessonItemRepository
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class GetLessonItemUseCase {
    @Inject
    lateinit var lessonItemRepository: LessonItemRepository

    init {
        DaggerLogicComponent
            .create()
            .initializeGetLessonItemUseCase(this)
    }

    fun doWork(params:Params):LessonItem{
        return lessonItemRepository.getLessonItem(params.id,params.date)
    }
    data class Params(
        val id: String?,
        val date: LocalDate
    )

}