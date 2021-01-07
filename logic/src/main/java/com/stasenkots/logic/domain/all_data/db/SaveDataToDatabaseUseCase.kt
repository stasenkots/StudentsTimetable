package com.stasenkots.logic.domain.all_data.db

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.entity.lesson.Lessons
import com.stasenkots.logic.entity.state.States
import com.stasenkots.logic.entity.student.Students
import com.stasenkots.logic.entity.subject.Subjects


class SaveDataToDatabaseUseCase : DatabaseUseCase() {
    init {
        DaggerLogicComponent
            .create()
            .initializeSaveDataToDatabaseUseCase(this)
    }

    override suspend fun doWork(params: Params) {
        lessonRepository.saveLessonsToDb(
            params.lessonDao,
            Lessons.get().values.toList()
        )

        stateRepository.saveStatesToDb(
            params.stateDao,
            States.get().values.toList()
        )
        studentRepository.saveStudentsToDb(
            params.studentDao,
            Students.get().values.toList()
        )
        subjectRepository.saveSubjectsToDb(
            params.subjectDao,
            Subjects.get().values.toList()
        )
    }

}