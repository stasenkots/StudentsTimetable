package com.stasenkots.logic.domain.all_data.server

import com.stasenkots.logic.repository.lessons.LessonRepository
import com.stasenkots.logic.repository.states.StatesRepository
import com.stasenkots.logic.repository.student.StudentRepository
import com.stasenkots.logic.repository.subject.SubjectRepository
import javax.inject.Inject

abstract class ServerUseCase {
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