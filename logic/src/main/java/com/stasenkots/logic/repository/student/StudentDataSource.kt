package com.stasenkots.logic.repository.student

import com.stasenkots.logic.db.dao.StudentDao
import com.stasenkots.logic.db.entity.StudentDb
import com.stasenkots.logic.network.dto.student.StudentsResponse
import com.stasenkots.logic.network.networking.StudentApi
import retrofit2.Response
import javax.inject.Inject

class StudentDataSource @Inject constructor(
    private val studentApi: StudentApi
) {
    suspend fun getStudents():Response<StudentsResponse>{
        return studentApi.getStudents()
    }
    suspend fun getStudentsFromDb(dao: StudentDao):List<StudentDb>{
        return dao.getStudents()
    }
    suspend fun cleanDb(dao: StudentDao){
        dao.cleanDb()
    }
    suspend fun saveStudentsToDb(dao: StudentDao,list: List<StudentDb>){
        dao.insertStudent(list)
    }
}