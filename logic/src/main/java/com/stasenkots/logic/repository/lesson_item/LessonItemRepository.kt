package com.stasenkots.logic.repository.lesson_item

import androidx.lifecycle.LifecycleOwner
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
        lifecycleOwner: LifecycleOwner,
        lessonsItems: MutableLiveData<MutableList<LessonItem>>,
        currentDate: LocalDate,
        lessonDao: LessonDao,
        subjectDao: SubjectDao,
        stateDao: StateDao
    ) {
        setLessonLiveQuery(lifecycleOwner, lessonsItems, currentDate, lessonDao)
        setSubjectLiveQuery(lifecycleOwner, lessonsItems, subjectDao)
        setStatesLiveQuery(lifecycleOwner, lessonsItems, stateDao)

    }

    private fun setLessonLiveQuery(
        lifecycleOwner: LifecycleOwner,
        lessonsItems: MutableLiveData<MutableList<LessonItem>>,
        currentDate: LocalDate,
        lessonDao: LessonDao
    ) {
        Lessons.modifiedObject.observe(lifecycleOwner, { lesson ->
            lessonRepository.updateData(lesson, currentDate, lessonsItems, lessonItemMapper)
            launchIO {
                lessonRepository.updateLessonInDb(lesson, lessonDao)
            }
        })
        Lessons.createdObject.observe(lifecycleOwner, { lesson ->
            lessonRepository.updateData(lesson, currentDate, lessonsItems, lessonItemMapper)
            launchIO {
                lessonRepository.insertLessonInDb(lesson, lessonDao)
            }
        })
        Lessons.deletedObject.observe(lifecycleOwner, { id ->
            lessonsItems.value?.removeIf { it.lesson == id }
            launchIO {
                lessonRepository.deleteLessonFromDb(id, lessonDao)
            }
            lessonsItems.postValue(lessonsItems.value)
        })
    }


    private fun setSubjectLiveQuery(
        lifecycleOwner: LifecycleOwner,
        lessonsItems: MutableLiveData<MutableList<LessonItem>>,
        subjectDao: SubjectDao
    ) {
        Subjects.modifiedObject.observe(lifecycleOwner, { subject ->
            subjectRepository.updateData(lessonsItems, subject)
            launchIO {
                subjectRepository.updateSubjectInDb(subject, subjectDao)
            }
        })
        Subjects.createdObject.observe(lifecycleOwner, { subject ->
            subjectRepository.updateData(lessonsItems, subject)
            launchIO {
                subjectRepository.insertSubjectInDb(subject, subjectDao)
            }
        })
    }

    private fun setStatesLiveQuery(
        lifecycleOwner: LifecycleOwner,
        lessonsItems: MutableLiveData<MutableList<LessonItem>>,
        stateDao: StateDao
    ) {
        States.modifiedObject.observe(lifecycleOwner, { state ->
            statesRepository.updateData(lessonsItems, state)
            launchIO {
                statesRepository.updateStateInDb(state, stateDao)
            }
        })
        States.createdObject.observe(lifecycleOwner,{state->
            statesRepository.updateData(lessonsItems, state)
            launchIO {
                statesRepository.insertStateInDb(state, stateDao)
            }
        })
        States.deletedObject.observe(lifecycleOwner, { id ->
            lessonsItems.value?.forEach { lessonItem ->
                if (lessonItem.state?.id == id) {
                    lessonItem.state = null
                    launchIO {
                        statesRepository.deleteStateFromDb(id, stateDao)
                    }
                }
            }
            lessonsItems.postValue(lessonsItems.value)
        })
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