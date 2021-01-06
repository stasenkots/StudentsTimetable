package com.stasenkots.logic.db.manager

import com.stasenkots.logic.db.dao.LessonDao
import com.stasenkots.logic.db.dao.StateDao
import com.stasenkots.logic.db.dao.StudentDao
import com.stasenkots.logic.db.dao.SubjectDao
import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.repository.lessons.LessonRepository
import com.stasenkots.logic.repository.states.StatesRepository
import com.stasenkots.logic.repository.student.StudentRepository
import com.stasenkots.logic.repository.subject.SubjectRepository
import javax.inject.Inject

 abstract class DatabaseWorker (
    protected val lessonDao: LessonDao,
    protected val subjectDao: SubjectDao,
    protected val stateDao: StateDao,
    protected val studentDao: StudentDao
) {

    @Inject
    lateinit var lessonRepository: LessonRepository

    @Inject
    lateinit var stateRepository: StatesRepository

    @Inject
    lateinit var studentRepository: StudentRepository

    @Inject
    lateinit var subjectRepository: SubjectRepository

    abstract suspend fun doWork()
}
