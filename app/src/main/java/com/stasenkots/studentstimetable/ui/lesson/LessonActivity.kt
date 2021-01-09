package com.stasenkots.studentstimetable.ui.lesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stasenkots.logic.utils.toLong
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.constants.ActionsConstants.ACTION_EDIT_LESSON
import com.stasenkots.studentstimetable.constants.MainActivityConstants.CURRENT_DATE_TAG
import com.stasenkots.studentstimetable.constants.MainActivityConstants.LESSON_ID_TAG
import com.stasenkots.studentstimetable.ui.lesson.edit.EditLessonFragment
import java.time.LocalDate

class LessonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)
        val action = intent.action
        val lessonId = intent?.getStringExtra(LESSON_ID_TAG)
        val date=intent?.getLongExtra(CURRENT_DATE_TAG,LocalDate.now().toLong())
        val fragment = when (action) {
            ACTION_EDIT_LESSON -> EditLessonFragment.newInstance(lessonId,date)
            else ->  throw Exception(getString(R.string.invalid_action))
        }
        supportFragmentManager.beginTransaction().replace(
            R.id.nav_host_fragment_lesson,
            fragment
        ).commit()

    }
}