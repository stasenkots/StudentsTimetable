package com.stasenkots.studentstimetable.ui.timetable.dialogs.day_of_week_picker

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.stasenkots.studentstimetable.R

private const val TAG_DAY = "day"
private const val TAG_WEEK = "week"

class WarningDialogFragment : DialogFragment() {
    val viewModel by lazy { ViewModelProvider(requireActivity()).get(DayOfWeekPickerViewModel::class.java) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val day = arguments?.getString(TAG_DAY).orEmpty()
        val week = arguments?.getString(TAG_WEEK).orEmpty()
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.warning)
            .setMessage(R.string.change_weekdays)
            .setPositiveButton(R.string.ok) { dialogInterface, id ->
                viewModel.setDayOfWeek(
                    String.format(
                        getString(R.string.day_of_week_pattern),
                        day,
                        week
                    )
                )
                dialog?.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialogInterface, id ->
                dialog?.dismiss()
            }
            .create()
    }

    companion object {
        fun newInstance(day: String, week: String): WarningDialogFragment {
            return WarningDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(TAG_DAY, day)
                    putString(TAG_WEEK, week)
                }
            }
        }
    }

}