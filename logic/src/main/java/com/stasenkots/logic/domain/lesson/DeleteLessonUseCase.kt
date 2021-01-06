package com.stasenkots.logic.domain.lesson

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.repository.lessons.LessonRepository
import javax.inject.Inject

class DeleteLessonUseCase {
    @Inject
    lateinit var lessonsRepository: LessonRepository


    init {
        DaggerLogicComponent
            .create()
            .initializeDeleteLessonUseCase(this)
    }

    suspend fun doWork(params:Params) {
        lessonsRepository.deleteLesson(params.lessonId)
    }
    data class Params(
        val lessonId:String
    )

}