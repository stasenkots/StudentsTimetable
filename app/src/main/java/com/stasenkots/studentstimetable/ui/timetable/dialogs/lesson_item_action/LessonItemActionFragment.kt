package com.stasenkots.studentstimetable.ui.timetable.dialogs.lesson_item_action

import android.app.Dialog
import android.os.Bundle
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.stasenkots.logic.utils.toLong
import com.stasenkots.studentstimetable.R
import java.time.LocalDate
import java.util.*

private const val TAG_LESSON_ID = "lesson id"
private const val TAG_DATE = "date"

class LessonItemActionFragment : DialogFragment() {
    private val actions = arrayOf(
        R.string.add_homework,
        R.string.list_of_passed_users,
        R.string.edit,
        R.string.delete,
        R.string.archive_homeworks
    )


    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(
            LessonItemActionViewModel::class.java
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val lessonId = arguments?.getString(TAG_LESSON_ID) ?: ""
        val date = arguments?.getLong(TAG_DATE)
        val items = actions.map { getString(it) }.toTypedArray()
        return MaterialAlertDialogBuilder(requireContext())
            .setItems(
                items
            ) { dialog, which: Int ->
                val actionId = actions[which]
                viewModel.setLessonItemAction(actionId, lessonId, date ?: LocalDate.now().toLong())
            }
            .create()
    }

    companion object {

        fun newInstance(lessonId: String, currentDate: Long): LessonItemActionFragment {
            return LessonItemActionFragment().apply {
                arguments = Bundle().apply {
                    putString(TAG_LESSON_ID, lessonId)
                    putLong(TAG_DATE, currentDate)
                }
            }
        }
    }

}