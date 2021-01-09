package com.stasenkots.studentstimetable.ui.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.ViewModelProvider
import com.stasenkots.logic.entity.lesson.Lessons
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.constants.ActionsConstants.ACTION_EDIT_HOMEWORK
import com.stasenkots.studentstimetable.constants.ActionsConstants.ACTION_VIEW_HOMEWORKS_ARCHIVE
import com.stasenkots.studentstimetable.constants.MainActivityConstants.CURRENT_DATE_TAG
import com.stasenkots.studentstimetable.constants.MainActivityConstants.LESSON_ID_TAG
import com.stasenkots.studentstimetable.ui.homework.archive.HomeworksArchiveFragment
import com.stasenkots.studentstimetable.ui.homework.edit.EditHomeworkFragment


class HomeworkActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProvider(this).get(HomeworkViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homework)
        val action = intent.action
        val lessonId = intent?.getStringExtra(LESSON_ID_TAG)
        val date=intent.getLongExtra(CURRENT_DATE_TAG,0)
        val subjectId= Lessons.get()[lessonId]?.subject
        val fragment = when (action) {
            ACTION_EDIT_HOMEWORK -> EditHomeworkFragment.newInstance(subjectId,date)
            ACTION_VIEW_HOMEWORKS_ARCHIVE -> HomeworksArchiveFragment.newInstance(subjectId)
            else ->  throw Exception(getString(R.string.invalid_action))
        }
        supportFragmentManager.beginTransaction().replace(
            R.id.nav_host_fragment_lesson,
            fragment
        ).commit()
    }
    val callback = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.contextual_action_bar, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
             return when (item?.itemId) {
                R.id.delete -> {
                    viewModel.deleteStates()
                    mode?.finish()
                    true
                }
                else -> false
            }

        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            viewModel.selectedItems.clear()
            viewModel.mode.postValue(NORMAL_MODE)
        }
    }

}