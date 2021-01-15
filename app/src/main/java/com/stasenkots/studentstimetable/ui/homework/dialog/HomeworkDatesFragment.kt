package com.stasenkots.studentstimetable.ui.homework.dialog

import android.app.Dialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.stasenkots.logic.utils.parseToString
import com.stasenkots.logic.utils.toLocalDate
import com.stasenkots.studentstimetable.R
import java.util.*

private const val DAY_TAG = "days"
private const val SUBJECT_ID_TAG = "subject id"

class HomeworkDatesFragment : DialogFragment() {

    companion object {
        fun newInstance(subjectId: String, date: Long) = HomeworkDatesFragment().apply {
            arguments = Bundle().apply {
                putString(SUBJECT_ID_TAG, subjectId)
                putLong(DAY_TAG, date)
            }
        }
    }

    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(HomeworkDatesViewModel::class.java) }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val day = arguments?.getLong(DAY_TAG) ?: 0L
        val subjectId = arguments?.getString(SUBJECT_ID_TAG).orEmpty()
        val dates = viewModel.getDates(subjectId, day)
        viewModel.init(dates)
        val datesStrings = dates.map { date ->
            date.parseToString()
        }
        var checkedItem = 0
        return MaterialAlertDialogBuilder(requireContext())
            .setSingleChoiceItems(
                datesStrings.toTypedArray(), viewModel.checkedItem
            ) { _, which ->
                checkedItem = which
            }
            .setPositiveButton(R.string.ok) { _: DialogInterface, _: Int ->
                viewModel.setDates(checkedItem)
                viewModel.checkedItem=checkedItem
            }
            .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .create()
    }

}