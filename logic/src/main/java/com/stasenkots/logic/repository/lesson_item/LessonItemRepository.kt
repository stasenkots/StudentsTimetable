package com.stasenkots.logic.repository.lesson_item

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stasenkots.logic.db.dao.LessonDao
import com.stasenkots.logic.db.dao.StateDao
import com.stasenkots.logic.db.dao.SubjectDao
import com.stasenkots.logic.entity.*
import com.stasenkots.logic.entity.lesson.Lesson
import com.stasenkots.logic.entity.lesson.Lessons
import com.stasenkots.logic.entity.state.States
import com.stasenkots.logic.entity.subject.Subject
import com.stasenkots.logic.entity.subject.Subjects
import com.stasenkots.logic.network.mappers.LessonItemMapper
import com.stasenkots.logic.repository.lessons.LessonRepository
import com.stasenkots.logic.repository.states.StatesRepository
import com.stasenkots.logic.repository.subject.SubjectRepository
import com.stasenkots.logic.utils.convertToDayOfWeek
import com.stasenkots.logic.utils.launchIO
import java.lang.Exception
import java.time.LocalDate
import javax.inject.Inject

class LessonItemRepository @Inject constructor(
    private val lessonItemMapper: LessonItemMapper,
    private val lessonRepository: LessonRepository,
    private val subjectRepository: SubjectRepository,
    private val statesRepository: StatesRepository
) {
    fun getLessonItems(currentDate: LocalDate): List<LessonItem> {
        val lessonItems = mutableListOf<LessonItem>()
        val dayOfWeek = currentDate.convertToDayOfWeek()
        val todayLessons = Lessons.get().values.filter { lesson ->
            lesson.dayOfWeek == dayOfWeek
        }
        for (lesson in todayLessons) {
            val lessonItem = lessonItemMapper.map(lesson, currentDate)
            lessonItems.add(lessonItem)
        }
        return lessonItems
    }

    fun setLiveQuery(
        lessonDao: LessonDao,
        subjectDao: SubjectDao,
        stateDao: StateDao
    ):LiveData<Unit> {
        val liveQuery = MutableLiveData<Unit>()
        setLessonLiveQuery(lessonDao, liveQuery)
        setSubjectLiveQuery(subjectDao,liveQuery)
        setStatesLiveQuery(stateDao,liveQuery)
        return liveQuery

    }

    private fun setLessonLiveQuery(
        lessonDao: LessonDao,
        liveQuery: MutableLiveData<Unit>
    ) {
        Lessons.modifiedObject.observeForever { lesson ->
            liveQuery.postValue(Unit)
            launchIO {
                lessonRepository.updateLessonInDb(lesson, lessonDao)
            }
        }
        Lessons.createdObject.observeForever { lesson ->
            liveQuery.postValue(Unit)
            launchIO {
                lessonRepository.insertLessonInDb(lesson, lessonDao)
            }
        }
        Lessons.deletedObject.observeForever { id ->
            liveQuery.postValue(Unit)
            launchIO {
                lessonRepository.deleteLessonFromDb(id, lessonDao)
            }
        }
    }


    private fun setSubjectLiveQuery(
        subjectDao: SubjectDao,
        liveQuery: MutableLiveData<Unit>
    ) {
        Subjects.modifiedObject.observeForever { subject ->
            liveQuery.postValue(Unit)
            launchIO {
                subjectRepository.updateSubjectInDb(subject, subjectDao)
            }
        }
        Subjects.createdObject.observeForever { subject ->
            liveQuery.postValue(Unit)
            launchIO {
                subjectRepository.insertSubjectInDb(subject, subjectDao)
            }
        }
    }

    private fun setStatesLiveQuery(
        stateDao: StateDao,
        liveQuery: MutableLiveData<Unit>
    ) {
        States.modifiedObject.observeForever { state ->
            liveQuery.postValue(Unit)
            launchIO {
                statesRepository.updateStateInDb(state, stateDao)
            }
        }
        States.createdObject.observeForever { state ->
            liveQuery.postValue(Unit)
            launchIO {
                statesRepository.insertStateInDb(state, stateDao)
            }
        }
        States.deletedObject.observeForever { id ->
            liveQuery.postValue(Unit)
            launchIO {
                statesRepository.deleteStateFromDb(id, stateDao)
            }
        }
    }


    fun getLessonItem(id: String?, date: LocalDate): LessonItem {
        val lesson = Lessons.get()[id]
        return lesson?.let { lessonItemMapper.map(it, date) } ?: LessonItem()
    }

    suspend fun sendLessonItem(lessonItem: LessonItem) {
        val lesson = lessonItemMapper.mapToLesson(lessonItem)
        val subject = lessonItemMapper.mapToSubject(lessonItem)
        if (subject.id.isEmpty()) {
            addLessonItem(subject, lesson)
        } else {
            subjectRepository.sendSubject(subject)
            lessonRepository.sendLesson(lesson)
        }
    }

    private suspend fun addLessonItem(subject: Subject, lesson: Lesson) {
        val s = Subjects.get().values.find {
            it.name == subject.name &&
                    it.subgroup == subject.subgroup &&
                    it.teacher == subject.teacher &&
                    it.type == subject.type
        }
        if (s != null) {
            subject.id = s.id
            lesson.subject = subject.id
            subjectRepository.sendSubject(subject)
            lessonRepository.sendLesson(lesson)
        } else {
            val requestResponse = subjectRepository.sendSubject(subject)
            lesson.subject = requestResponse?.objectId ?: throw Exception("Empty subjectId")
            lessonRepository.sendLesson(lesson)
        }
    }
}