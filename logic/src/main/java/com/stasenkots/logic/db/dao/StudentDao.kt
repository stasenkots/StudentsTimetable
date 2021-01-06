package com.stasenkots.logic.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.stasenkots.logic.db.entity.LessonDb
import com.stasenkots.logic.db.entity.StudentDb
import com.stasenkots.logic.db.entity.SubjectDb

@Dao
interface StudentDao{
    @Insert
    suspend fun insertStudent(studentsDb:List<StudentDb>)

    @Query("SELECT * FROM student_table")
    suspend fun getStudents():List<StudentDb>

    @Query("DELETE FROM student_table")
    suspend fun cleanDb()

}
