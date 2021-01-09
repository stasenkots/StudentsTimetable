package com.stasenkots.logic.repository.subject

import androidx.lifecycle.MutableLiveData
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.livequery.ParseLiveQueryClient
import com.parse.livequery.SubscriptionHandling
import com.stasenkots.logic.db.dao.SubjectDao
import com.stasenkots.logic.entity.LessonItem
import com.stasenkots.logic.entity.subject.Subject
import com.stasenkots.logic.entity.subject.Subjects
import com.stasenkots.logic.network.dto.RequestResponse
import com.stasenkots.logic.network.mappers.SubjectMapper
import java.lang.Exception
import javax.inject.Inject

class SubjectRepository @Inject constructor(
    private val dataSource: SubjectDataSource,
    private val mapper: SubjectMapper,
    private val dbMapper: com.stasenkots.logic.db.mappers.SubjectMapper,
    private val parseLiveQueryClient: ParseLiveQueryClient
) {
    suspend fun getSubjects(): List<Subject> {
        val response = dataSource.getSubjects()
        return if (response.isSuccessful) {
            response.body()?.subjectResponses?.map { subjectResponse ->
                mapper.map(subjectResponse)
            } ?: emptyList()
        } else {
            throw Exception(response.message())
        }
    }

    suspend fun cleanDb(dao: SubjectDao) {
        dataSource.cleanDb(dao)
    }

    suspend fun getSubjectsFromDb(dao: SubjectDao): List<Subject> {
        return dataSource.getSubjectsFromDb(dao).map {
            dbMapper.map(it)
        }
    }

    suspend fun saveSubjectsToDb(dao: SubjectDao, list: List<Subject>) {
        dataSource.saveSubject(dao, list.map { dbMapper.map(it) })
    }


    suspend fun sendSubject(subject: Subject): RequestResponse? {
        val request = mapper.map(subject)
        return if (subject.id.isEmpty()) {
            dataSource.putSubject(request)
        } else {
            dataSource.updateSubject(subject.id, request)
            null
        }

    }

    fun updateData(lessonsItems: MutableLiveData<MutableList<LessonItem>>, subject: Subject) {
        val lessonsItem = lessonsItems.value?.find { it.subject==subject.id }
        lessonsItem?.let {
            with(it) {
                if (this.subject == subject.id) {
                    type = subject.type
                    name = subject.name
                    teacher = subject.teacher
                    subgroup = subject.subgroup
                }
            }
            lessonsItems.postValue(lessonsItems.value)
        }
    }

    suspend fun updateSubjectInDb(subject: Subject, dao: SubjectDao) {
        dataSource.updateSubjectInDb(dbMapper.map(subject), dao)
    }

    suspend fun insertSubjectInDb(subject: Subject, dao: SubjectDao) {
        dataSource.insertSubjectInDb(dbMapper.map(subject), dao)
    }

    fun setLiveQuery() {
        val query = ParseQuery.getQuery<ParseObject>("subject")
        val subscriptionHandling: SubscriptionHandling<ParseObject> =
            parseLiveQueryClient.subscribe(query)
        subscriptionHandling.handleEvents { _, event, mObject ->
            val subject = mapper.map(mObject)
            when (event) {
                SubscriptionHandling.Event.UPDATE -> {
                    Subjects.map[subject.id] = subject
                    Subjects.modifiedObject.postValue(subject)
                }
                SubscriptionHandling.Event.CREATE -> {
                    Subjects.map[subject.id] = subject
                    Subjects.createdObject.postValue(subject)
                }
            }

        }
        subscriptionHandling.handleError { _, error ->
            throw Exception(error)
        }
    }
}