package com.stasenkots.logic.repository.subject


import com.stasenkots.logic.db.dao.SubjectDao
import com.stasenkots.logic.db.entity.SubjectDb
import com.stasenkots.logic.network.dto.RequestResponse
import com.stasenkots.logic.network.dto.subject.request.SubjectRequest
import com.stasenkots.logic.network.dto.subject.response.SubjectsResponse
import com.stasenkots.logic.network.networking.SubjectApi
import retrofit2.Response
import javax.inject.Inject

class SubjectDataSource @Inject constructor(
    private val subjectApi: SubjectApi
) {
    suspend fun getSubjects(): Response<SubjectsResponse> {
        return subjectApi.getSubjects()
    }
    suspend fun getSubjectsFromDb(dao: SubjectDao): List<SubjectDb> {
        return dao.getSubjects()
    }
    suspend fun cleanDb(dao: SubjectDao){
        dao.cleanDb()
    }
    suspend fun insertSubjectInDb(subjectDb: SubjectDb,dao: SubjectDao){
        dao.insertSubject(subjectDb)
    }
    suspend fun putSubject(subjectRequest: SubjectRequest): RequestResponse? {
        return subjectApi.putSubject(subjectRequest).body()
    }
    suspend fun saveSubject(dao: SubjectDao,list: List<SubjectDb>) {
        dao.insertSubjects(list)
    }
    suspend fun updateSubjectInDb(subjectDb:SubjectDb,dao: SubjectDao){
        dao.updateSubject(subjectDb)
    }
    suspend fun updateSubject(objectId:String,subjectRequest: SubjectRequest) {
        subjectApi.updateSubject(objectId,subjectRequest)
    }
}