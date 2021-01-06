package com.stasenkots.logic.domain.lesson

import android.util.Log
import com.stasenkots.logic.db.dao.LessonDao
import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.lesson.Lesson
import com.stasenkots.logic.entity.lesson.Lessons
import com.stasenkots.logic.repository.lessons.LessonRepository
import com.stasenkots.logic.utils.launchAsync
import com.stasenkots.logic.utils.launchIO
import java.lang.Exception
import javax.inject.Inject

class LoadLessonsUseCase {
    @Inject
    lateinit var lessonsRepository: LessonRepository

    init {
        DaggerLogicComponent
            .create()
            .initializeLoadLessonsUseCase(this)
    }

    suspend fun doWork(): List<Lesson> {
        val lessons = lessonsRepository.getLessons()
        lessonsRepository.setLiveQuery()
        return lessons
    }
}