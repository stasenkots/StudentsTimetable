package com.stasenkots.logic.db.manager

import com.stasenkots.logic.db.dao.LessonDao
import com.stasenkots.logic.db.dao.StateDao
import com.stasenkots.logic.db.dao.StudentDao
import com.stasenkots.logic.db.dao.SubjectDao
import com.stasenkots.logic.di.components.DaggerLogicComponent

class DataBaseCleaner(
    lessonDao: LessonDao,
    subjectDao: SubjectDao,
    stateDao: StateDao,
    studentDao: StudentDao
) : DatabaseWorker(lessonDao, subjectDao, stateDao, studentDao) {
    init {
        DaggerLogicComponent
            .create()
            .initializeDatabaseCleaner(this)
    }

    override suspend fun doWork() {
        lessonRepository.clearDb(lessonDao)
        subjectRepository.cleanDb(subjectDao)
        stateRepository.cleanDb(stateDao)
        studentRepository.cleanDb(studentDao)

    }
}