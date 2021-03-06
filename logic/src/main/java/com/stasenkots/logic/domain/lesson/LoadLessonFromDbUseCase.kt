package com.stasenkots.logic.domain.lesson

import com.stasenkots.logic.db.dao.LessonDao
import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.lesson.Lesson
import com.stasenkots.logic.repository.lessons.LessonRepository
import javax.inject.Inject

class LoadLessonFromDbUseCase {
    @Inject
    lateinit var lessonsRepository: LessonRepository

    init {
        DaggerLogicComponent
            .create()
            .initializeLoadLessonsFromDbUseCase(this)
    }

    suspend fun doWork(params:Params):List<Lesson> {
       return lessonsRepository.getLessonsFromDb(params.dao)
    }
    data class Params(val dao: LessonDao)
}