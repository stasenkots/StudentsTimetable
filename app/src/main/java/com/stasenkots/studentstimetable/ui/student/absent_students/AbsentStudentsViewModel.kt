package com.stasenkots.studentstimetable.ui.student.absent_students

import android.util.SparseBooleanArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stasenkots.logic.domain.states.SendStateUseCase
import com.stasenkots.logic.entity.state.State
import com.stasenkots.logic.entity.state.States
import com.stasenkots.logic.entity.student.Student
import com.stasenkots.logic.entity.student.Students
import com.stasenkots.logic.utils.launchIO
import com.stasenkots.studentstimetable.Analytics
import timber.log.Timber
import java.lang.Exception
import java.time.LocalDate

class AbsentStudentsViewModel(private val analytics: Analytics) : ViewModel() {
    lateinit var students: List<Student>
    private lateinit var state: State
    private lateinit var date: LocalDate
    private val sendStateUseCase=SendStateUseCase()
    val checkedItemPositions= SparseBooleanArray()
    private  val _errorBus=MutableLiveData<Exception?>()
    val errorBus:LiveData<Exception?>
    get() = _errorBus
    fun init(subjectId: String, currentDate: LocalDate) {
        state = States.get().values.find {
            it.date == currentDate &&
                    it.subject == subjectId
        } ?: State(subject = subjectId, date = currentDate, absentUsers = emptyList())
        date = currentDate
        students = Students.get().values.toList()
        students.forEachIndexed { index, student ->
            checkedItemPositions.append(index,state.absentUsers.contains(student.id))
        }
    }

    fun save(checkedItemPositions: SparseBooleanArray) {
        state.absentUsers = students.filterIndexed { index, _ ->
            checkedItemPositions[index]
        }.map { it.id }
        launchIO {
            try {
                sendStateUseCase.doWork(SendStateUseCase.Params(state))
                _errorBus.postValue(null)
            }catch (e:Exception){
                analytics.logError(e)
                Timber.e(e)
                _errorBus.postValue(e)
            }

        }
    }


}