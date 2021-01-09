package com.stasenkots.studentstimetable.ui.timetable

import android.app.Application
import android.icu.util.LocaleData
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stasenkots.logic.db.database.LessonDatabaseProvider
import com.stasenkots.logic.db.database.StateDatabaseProvider
import com.stasenkots.logic.db.database.StudentDatabaseProvider
import com.stasenkots.logic.db.database.SubjectDatabaseProvider
import com.stasenkots.logic.domain.all_data.db.CleanDatabaseUseCase
import com.stasenkots.logic.domain.all_data.db.DatabaseUseCase
import com.stasenkots.logic.domain.all_data.db.SaveDataToDatabaseUseCase
import com.stasenkots.logic.domain.all_data.server.LoadAllDataUseCase
import com.stasenkots.logic.utils.launchIO
import java.time.LocalDate
import java.util.*

class TimeTableViewModel(val app: Application) : AndroidViewModel(app) {

    private val lessonDao = LessonDatabaseProvider.provide(app.applicationContext).getDao()
    private val subjectDao = SubjectDatabaseProvider.provide(app.applicationContext).getDao()
    private val stateDao = StateDatabaseProvider.provide(app.applicationContext).getDao()
    private val studentDao = StudentDatabaseProvider.provide(app.applicationContext).getDao()
    var selectedDate: LocalDate =LocalDate.now()
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
            LoadAllDataUseCase().doWork()
            _isDataLoaded.postValue(true)
            launchIO {
                CleanDatabaseUseCase()
                    .doWork(
                        DatabaseUseCase.Params(
                            lessonDao,
                            subjectDao,
                            stateDao,
                            studentDao
                        )
                    )
                SaveDataToDatabaseUseCase()
                    .doWork(
                        DatabaseUseCase.Params(
                            lessonDao,
                            subjectDao,
                            stateDao,
                            studentDao
                        )
                    )
            }
        } catch (e: java.lang.Exception) {
            _errorBus.postValue(e)
        }

    }


}