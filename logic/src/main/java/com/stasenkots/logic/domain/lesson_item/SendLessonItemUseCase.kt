package com.stasenkots.logic.domain.lesson_item

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.LessonItem
import com.stasenkots.logic.repository.lesson_item.LessonItemRepository
import java.util.*
import javax.inject.Inject

class SendLessonItemUseCase {
    @Inject
    lateinit var lessonItemRepository: LessonItemRepository

    init {
        DaggerLogicComponent
            .create()
            .initializeSendLessonItemUseCase(this)
    }

    suspend fun doWork(params:Params){
         lessonItemRepository.sendLessonItem(params.lessonItem)
    }
    data class Params(
        val lessonItem: LessonItem
    )
}