package com.stasenkots.studentstimetable.workmanager

import android.app.NotificationManager
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.stasenkots.logic.db.dao.LessonDao
import com.stasenkots.logic.db.dao.StateDao
import com.stasenkots.logic.db.dao.StudentDao
import com.stasenkots.logic.db.dao.SubjectDao
import com.stasenkots.logic.db.database.LessonDatabaseProvider
import com.stasenkots.logic.db.database.StateDatabaseProvider
import com.stasenkots.logic.db.database.StudentDatabaseProvider
import com.stasenkots.logic.db.database.SubjectDatabaseProvider
import com.stasenkots.logic.domain.all_data.db.DatabaseUseCase
import com.stasenkots.logic.domain.all_data.db.LoadAllDataFromDatabaseUseCase
import com.stasenkots.logic.domain.lesson.LoadLessonsUseCase
import com.stasenkots.logic.domain.states.LoadStatesUseCase
import com.stasenkots.logic.domain.student.LoadStudentsUseCase
import com.stasenkots.logic.domain.subject.LoadSubjectsUseCase
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.entity.lesson.Lessons
import com.stasenkots.logic.entity.state.States
import com.stasenkots.logic.entity.student.Students
import com.stasenkots.logic.entity.subject.Subjects
import com.stasenkots.logic.utils.MODE_MODERATOR
import com.stasenkots.studentstimetable.constants.CheckForUpdatesConstants.LESSON_DAO
import com.stasenkots.studentstimetable.notification.NotificationTimeTableManager
import java.lang.Exception

class CheckForUpdatesWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    private val loadLessonsUseCase = LoadLessonsUseCase()
    private val loadStatesUseCase = LoadStatesUseCase()
    private val loadSubjectUseCase = LoadSubjectsUseCase()
    private val loadStudentsUseCase = LoadStudentsUseCase()
    private val loadAllDataFromDatabaseUseCase = LoadAllDataFromDatabaseUseCase()
    private val lessonDao = LessonDatabaseProvider.provide(applicationContext).getDao()
    private val subjectDao = SubjectDatabaseProvider.provide(applicationContext).getDao()
    private val stateDao = StateDatabaseProvider.provide(applicationContext).getDao()
    private val studentDao = StudentDatabaseProvider.provide(applicationContext).getDao()

    override suspend fun doWork(): Result {
        try {
            loadAllDataFromDatabaseUseCase.doWork(
                DatabaseUseCase.Params(
                    lessonDao,
                    subjectDao,
                    stateDao,
                    studentDao
                )
            )
            val newStates = loadStatesUseCase.doWork()
            val notificationManager = NotificationTimeTableManager()
            if (States.get().values.toList() != newStates) {
                notificationManager.notify(applicationContext)
            }
            val newLessons = loadLessonsUseCase.doWork()
            if (Lessons.get().values.toList() != newLessons) {
                notificationManager.notify(applicationContext)
            }
            val newSubjects = loadSubjectUseCase.doWork()
            if (Subjects.get().values.toList() != newSubjects) {
                notificationManager.notify(applicationContext)
            }
            if (User.mode == MODE_MODERATOR) {
                val newStudents = loadStudentsUseCase.doWork()
                if (Students.get().values.toList() != newStudents) {
                    notificationManager.notify(applicationContext)
                }
            }
            return Result.success()
        }catch (e:Exception){
            return Result.retry()
        }
    }
}