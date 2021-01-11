package com.stasenkots.logic.repository.lessons

import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.livequery.ParseLiveQueryClient
import com.parse.livequery.SubscriptionHandling
import com.stasenkots.logic.db.dao.LessonDao
import com.stasenkots.logic.entity.lesson.Lesson
import com.stasenkots.logic.entity.lesson.Lessons
import com.stasenkots.logic.network.mappers.LessonMapper
import java.util.*
import javax.inject.Inject

class LessonRepository @Inject constructor(
    private val dataSource: LessonsDataSource,
    private val mapper: LessonMapper,
    private val dbMapper: com.stasenkots.logic.db.mappers.LessonMapper,
    private val parseLiveQueryClient: ParseLiveQueryClient
) {
    suspend fun getLessons(): List<Lesson> {
        val response = dataSource.getLessons()
        return if (response.isSuccessful) {
            response.body()?.lessonResponses?.map { lessonResponse ->
                mapper.map(lessonResponse)
            } ?: emptyList()
        } else {
            throw Exception(response.message())
        }
    }

    suspend fun clearDb(dao: LessonDao) {
        dataSource.cleanDb(dao)
    }

    suspend fun getLessonsFromDb(dao: LessonDao): List<Lesson> {
        return dataSource.getLessonsFromDb(dao).map {
            dbMapper.map(it)
        }
    }

    suspend fun updateLessonInDb(lesson: Lesson, dao: LessonDao) {
        dataSource.updateLessonInDb(dbMapper.map(lesson), dao)
    }

    suspend fun deleteLessonFromDb(id: String, dao: LessonDao) {
        dataSource.deleteLessonFromDb(id, dao)
    }


    suspend fun sendLesson(lesson: Lesson) {

        val request = mapper.map(lesson)
        if (lesson.id.isEmpty()) {
            dataSource.putLesson(request)
        } else {
            dataSource.updateLesson(lesson.id, request)
        }
    }

    suspend fun saveLessonsToDb(dao: LessonDao, lessons: List<Lesson>) {
        dataSource.saveLessonsToDb(dao, lessons.map { dbMapper.map(it) })
    }

    suspend fun deleteLesson(id: String) {
        dataSource.deleteLesson(id)
    }

    suspend fun insertLessonInDb(lesson: Lesson, dao: LessonDao) {
        dataSource.insertLessonInDb(dbMapper.map(lesson), dao)
    }

    fun setLiveQuery() {
        val query = ParseQuery.getQuery<ParseObject>("lesson")
        val subscriptionHandling: SubscriptionHandling<ParseObject> =
            parseLiveQueryClient.subscribe(query)
        subscriptionHandling.handleEvents { _, event, mObject ->
            val lesson = mapper.map(mObject)
            when (event) {
                SubscriptionHandling.Event.DELETE -> {
                    Lessons.deletedObject.postValue(lesson.id)
                    Lessons.map.remove(lesson.id)
                }
                SubscriptionHandling.Event.UPDATE -> {
                    Lessons.map[lesson.id] = lesson
                    Lessons.modifiedObject.postValue(lesson)
                }
                SubscriptionHandling.Event.CREATE -> {
                    Lessons.map[lesson.id] = lesson
                    Lessons.createdObject.postValue(lesson)
                }
            }
        }
        subscriptionHandling.handleError { _, error ->
            throw Exception(error)
        }
    }
}