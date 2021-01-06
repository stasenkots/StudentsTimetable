package com.stasenkots.studentstimetable.ui.timetable

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stasenkots.logic.db.database.LessonDatabaseProvider
import com.stasenkots.logic.db.database.StateDatabaseProvider
import com.stasenkots.logic.db.database.StudentDatabaseProvider
import com.stasenkots.logic.db.database.SubjectDatabaseProvider
import com.stasenkots.logic.db.manager.DataBaseCleaner
import com.stasenkots.logic.db.manager.DatabaseSaver
import com.stasenkots.logic.domain.lesson.LoadLessonsUseCase
import com.stasenkots.logic.domain.lesson.SaveLessonsToDbUseCase
import com.stasenkots.logic.domain.states.LoadStatesUseCase
import com.stasenkots.logic.domain.states.SaveStatesToDbUseCase
import com.stasenkots.logic.domain.student.LoadStudentsUseCase
import com.stasenkots.logic.domain.student.SaveStudentsToDbUseCase
import com.stasenkots.logic.domain.subject.LoadSubjectsUseCase
import com.stasenkots.logic.domain.subject.SaveSubjectsToDbUseCase
import com.stasenkots.logic.entity.lesson.Lessons
import com.stasenkots.logic.entity.state.States
import com.stasenkots.logic.entity.student.Students
import com.stasenkots.logic.entity.subject.Subjects
import com.stasenkots.logic.network.manager.ServerLoader
import com.stasenkots.logic.utils.launchIO

class TimeTableViewModel(val app: Application) : AndroidViewModel(app) {

    private val lessonDao = LessonDatabaseProvider.provide(app.applicationContext).getDao()
    private val subjectDao = SubjectDatabaseProvider.provide(app.applicationContext).getDao()
    private val stateDao = StateDatabaseProvider.provide(app.applicationContext).getDao()
    private val studentDao = StudentDatabaseProvider.provide(app.applicationContext).getDao()
    private val _isDataLoaded = MutableLiveData<Boolean>()
    private val _errorBus = MutableLiveData<Exception>()
    val errorBus: LiveData<Exception>
        get() = _errorBus
    val isDataLoaded: LiveData<Boolean>
        get() = _isDataLoaded

    init {
        launchIO {
            load()
        }
    }

    private suspend fun load() {
        try {
            ServerLoader().doWork()
            _isDataLoaded.postValue(true)
            launchIO {
                DataBaseCleaner(
                    lessonDao,
                    subjectDao,
                    stateDao,
                    studentDao
                ).doWork()
                DatabaseSaver(
                    lessonDao,
                    subjectDao,
                    stateDao,
                    studentDao
                ).doWork()
            }
        } catch (e: java.lang.Exception) {
            _errorBus.postValue(e)
        }

    }


}