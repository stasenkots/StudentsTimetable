package com.stasenkots.logic.domain.all_data.server

import com.stasenkots.logic.di.components.DaggerLogicComponent
import com.stasenkots.logic.domain.lesson.LoadLessonsUseCase
import com.stasenkots.logic.domain.states.LoadStatesUseCase
import com.stasenkots.logic.domain.student.LoadStudentsUseCase
import com.stasenkots.logic.domain.subject.LoadSubjectsUseCase
import com.stasenkots.logic.entity.Group
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.entity.lesson.Lessons
import com.stasenkots.logic.entity.state.States
import com.stasenkots.logic.entity.student.Students
import com.stasenkots.logic.entity.subject.Subjects

class LoadAllDataUseCase: ServerUseCase(){

    init {
        DaggerLogicComponent
            .create()
            .initializeLoadAllDataUseCase(this)
    }
    override suspend fun doWork(){
        val subjects = subjectRepository.getSubjects()
        Subjects.put(subjects)
        subjectRepository.setLiveQuery()
        val lessons = lessonRepository.getLessons()
        Lessons.put(lessons)
        lessonRepository.setLiveQuery()
        val states = stateRepository.getStates()
        States.put(states)
        stateRepository.setLiveQuery()
        val students = studentRepository.getStudents()
        Students.put(students)
        val group = groupRepository.getGroup(User.groupId)
        Group.mGroup.groupId = group.groupId
        Group.mGroup.semStartDate =group.semStartDate
    }
}