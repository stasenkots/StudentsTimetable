package com.stasenkots.logic.repository.lessons

import com.stasenkots.logic.db.dao.LessonDao
import com.stasenkots.logic.db.entity.LessonDb
import com.stasenkots.logic.network.dto.lesson.request.LessonRequest
import com.stasenkots.logic.network.dto.lesson.response.LessonsResponse
import com.stasenkots.logic.network.networking.LessonApi
import retrofit2.Response
import javax.inject.Inject

class LessonsDataSource @Inject constructor(
    private val lessonApi: LessonApi
) {
    suspend fun getLessons(): Response<LessonsResponse> {
        return lessonApi.getLessons()
    }
    suspend fun cleanDb(dao: LessonDao){
        dao.cleanDb()
    }
    suspend fun putLesson(lessonResponse: LessonRequest) {
        lessonApi.putLesson(lessonResponse)
    }
    suspend fun updateLesson(objectId:String,lessonRequest:LessonRequest) {
        lessonApi.updateLesson(objectId,lessonRequest)
    }
    suspend fun deleteLesson(id: String){
        lessonApi.deleteLesson(id)
    }
    suspend fun insertLessonInDb(lessonDb: LessonDb,dao: LessonDao){
        dao.insertLesson(lessonDb)
    }
    suspend fun getLessonsFromDb(dao: LessonDao):List<LessonDb>{
        return dao.getLessons()
    }
    suspend fun updateLessonInDb(lessonDb: LessonDb,dao: LessonDao){
        dao.updateLesson(lessonDb)
    }
    suspend fun deleteLessonFromDb(id: String, dao: LessonDao){
        dao.deleteLesson(id)
    }
    suspend fun saveLessonsToDb(dao: LessonDao,lessons:List<LessonDb>){
        return dao.insertLessons(lessons)
    }

}