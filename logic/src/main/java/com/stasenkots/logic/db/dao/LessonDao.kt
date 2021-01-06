package com.stasenkots.logic.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.stasenkots.logic.db.entity.LessonDb
import retrofit2.http.DELETE

@Dao
interface LessonDao {
    @Insert
    suspend fun insertLessons(lessonsDb:List<LessonDb>)

    @Query("SELECT * FROM lesson_table")
    suspend fun getLessons():List<LessonDb>

    @Update
    suspend fun updateLesson(lesson:LessonDb)

    @Insert
    suspend fun insertLesson(lesson: LessonDb)

    @Query("DELETE FROM lesson_table WHERE objectId=:objectId")
    suspend fun deleteLesson(objectId:String)

    @Query("DELETE FROM lesson_table")
    suspend fun cleanDb()

}