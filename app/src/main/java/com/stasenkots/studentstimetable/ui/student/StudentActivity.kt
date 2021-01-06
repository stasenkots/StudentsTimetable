package com.stasenkots.studentstimetable.ui.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.stasenkots.logic.entity.lesson.Lessons
import com.stasenkots.studentstimetable.ACTION_VIEW_ABSENT_USERS
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.databinding.ActivityStudentBinding
import com.stasenkots.studentstimetable.ui.homework.archive.HomeworksArchiveFragment
import com.stasenkots.studentstimetable.ui.homework.edit.EditHomeworkFragment
import com.stasenkots.studentstimetable.ui.student.absent_students.AbsentStudentsFragment
import com.stasenkots.studentstimetable.ui.timetable.CURRENT_DATE_TAG
import com.stasenkots.studentstimetable.ui.timetable.LESSON_ID_TAG
import com.stasenkots.studentstimetable.ui.timetable.LESSON_ITEM_ACTION_TAG

class StudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val action = intent.action
        val lessonId = intent?.getStringExtra(LESSON_ID_TAG)
        val date=intent.getLongExtra(CURRENT_DATE_TAG,0)
        val subjectId= Lessons.get()[lessonId]?.subject
        val fragment = when (action) {
            ACTION_VIEW_ABSENT_USERS -> AbsentStudentsFragment.newInstance(subjectId,date)
            else ->  throw Exception(getString(R.string.invalid_action))
        }

        supportFragmentManager.beginTransaction().replace(
            R.id.nav_host_fragment_student,
            fragment
        ).commit()
    }

}