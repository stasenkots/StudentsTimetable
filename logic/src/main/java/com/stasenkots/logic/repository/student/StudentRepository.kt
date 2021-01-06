package com.stasenkots.logic.repository.student

import com.stasenkots.logic.db.dao.StudentDao
import com.stasenkots.logic.entity.student.Student
import com.stasenkots.logic.network.mappers.StudentMapper
import java.lang.Exception
import javax.inject.Inject

class StudentRepository @Inject constructor(
    private val dataSource: StudentDataSource,
    private val mapper:StudentMapper,
    private val dbMapper:com.stasenkots.logic.db.mappers.StudentMapper
) {
    suspend fun getStudents():List<Student>{
       val response= dataSource.getStudents()
        if (response.isSuccessful){
            return response.body()?.studentResponses?.map { studentResponse->
                mapper.map(studentResponse)
            }?: emptyList()
        }else {
            throw Exception(response.message())
        }
    }
    suspend fun cleanDb(dao: StudentDao){
        dataSource.cleanDb(dao)
    }
    suspend fun getStudentsFromDb(dao:StudentDao):List<Student>{
       return dataSource.getStudentsFromDb(dao).map {
           dbMapper.map(it)
       }
    }
    suspend fun saveStudentsToDb(dao: StudentDao,list: List<Student>){
        dataSource.saveStudentsToDb(dao,list.map { dbMapper.map(it) })
    }
}