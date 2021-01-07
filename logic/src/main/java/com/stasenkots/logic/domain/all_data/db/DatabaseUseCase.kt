package com.stasenkots.logic.domain.all_data.db

import com.stasenkots.logic.db.dao.LessonDao
import com.stasenkots.logic.db.dao.StateDao
import com.stasenkots.logic.db.dao.StudentDao
import com.stasenkots.logic.db.dao.SubjectDao
import com.stasenkots.logic.repository.lessons.LessonRepository
import com.stasenkots.logic.repository.states.StatesRepository
import com.stasenkots.logic.repository.student.StudentRepository
import com.stasenkots.logic.repository.subject.SubjectRepository
import javax.inject.Inject

 abstract class DatabaseUseCase (
) {

    @Inject
    lateinit var lessonRepository: LessonRepository

    @Inject
    lateinit var stateRepository: StatesRepository

    @Inject
    lateinit var studentRepository: StudentRepository

    @Inject
    lateinit var subjectRepository: SubjectRepository

    abstract suspend fun doWork(params: Params)
     data class Params(  val lessonDao: LessonDao,
                         val subjectDao: SubjectDao,
                         val stateDao: StateDao,
                         val studentDao: StudentDao)
}
