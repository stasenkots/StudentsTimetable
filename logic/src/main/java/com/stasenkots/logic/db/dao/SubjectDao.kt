package com.stasenkots.logic.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.stasenkots.logic.db.entity.LessonDb
import com.stasenkots.logic.db.entity.SubjectDb

@Dao
interface SubjectDao {
    @Insert
    suspend fun insertSubjects(subjectsDb: List<SubjectDb>)

    @Query("SELECT * FROM subject_table")
    suspend fun getSubjects(): List<SubjectDb>

    @Update
    suspend fun updateSubject(subjectDb: SubjectDb)

    @Insert
    suspend fun insertSubject(subjectDb: SubjectDb)

    @Query("DELETE  FROM subject_table")
    suspend fun cleanDb()


}
