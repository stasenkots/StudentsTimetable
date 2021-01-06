package com.stasenkots.logic.domain.lesson

import com.stasenkots.logic.db.dao.LessonDao
import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.lesson.Lesson
import com.stasenkots.logic.repository.lessons.LessonRepository
import javax.inject.Inject

class SaveLessonsToDbUseCase {
    @Inject
    lateinit var lessonsRepository: LessonRepository

    init {
        DaggerLogicComponent
            .create()
            .initializeSaveLessonsFromDbUseCase(this)
    }

    suspend fun doWork(params:Params){
        lessonsRepository.saveLessonsToDb(params.dao,params.list)
    }
    data class Params(val dao: LessonDao,val list: List<Lesson>)
}