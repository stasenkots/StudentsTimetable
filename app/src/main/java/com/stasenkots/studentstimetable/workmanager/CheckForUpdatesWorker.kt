package com.stasenkots.studentstimetable.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
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
import com.stasenkots.studentstimetable.Analytics
import com.stasenkots.studentstimetable.notification.NotificationTimeTableManager
import timber.log.Timber
import java.lang.Exception

class CheckForUpdatesWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    private val analytics = Analytics(applicationContext)
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
            val notificationManager = NotificationTimeTableManager()
            val newStatesUpdatesMap = loadStatesUseCase.doWork().associate { it.id to it.updatedAt }
            val oldStatesUpdatesMap = States.get().mapValues { it.value.updatedAt }
            if (newStatesUpdatesMap != oldStatesUpdatesMap) {
                notificationManager.notify(applicationContext)
                return Result.success()
            }
            val newLessonUpdatesMap =
                loadLessonsUseCase.doWork().associate { it.id to it.updatedAt }
            val oldLessonUpdatesMap = Lessons.get().mapValues { it.value.updatedAt }
            if (newLessonUpdatesMap != oldLessonUpdatesMap) {
                notificationManager.notify(applicationContext)
                return Result.success()
            }
            val newSubjectsUpdatesMap = loadSubjectUseCase.doWork().associate { it.id to it.updatedAt }
            val oldSubjectsUpdatesMap = Subjects.get().mapValues {it.value.updatedAt }
            if (newSubjectsUpdatesMap!=oldSubjectsUpdatesMap) {
                notificationManager.notify(applicationContext)
                return Result.success()
            }
            if (User.mode == MODE_MODERATOR) {
                val newStudentsUpdatesMap = loadStudentsUseCase.doWork().associate { it.id to it.updatedAt }
                val oldStudentsUpdatesMap = Students.get().mapValues {  it.value.updatedAt }
                if (newStudentsUpdatesMap!=oldStudentsUpdatesMap) {
                    notificationManager.notify(applicationContext)
                    return Result.success()
                }
            }
            return Result.success()
        } catch (e: Exception) {
            analytics.logError(e)
            Timber.e(e)
            return Result.retry()
        }
    }
}