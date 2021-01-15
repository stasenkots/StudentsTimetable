package com.stasenkots.studentstimetable.ui.timetable.dialogs.timepicker

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.ui.timetable.dialogs.lesson_item_action.LessonItemActionViewModel
import java.sql.Time

private const val HOUR_TAG = "hour tag"
private const val MINUTE_TAG = "minute tag"
private const val TYPE_TAG = "type tag"

class TimePickerFragment : DialogFragment() {
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(
        TimePickerViewModel::class.java) }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val hourArg = arguments?.getInt(HOUR_TAG) ?: 0
        val minuteArg = arguments?.getInt(MINUTE_TAG) ?: 0
        val viewId = arguments?.getInt(TYPE_TAG) ?: 0
        return TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                viewModel.setTime(hourOfDay, minute,viewId)
            }, hourArg, minuteArg, true
        )
    }

    companion object {
        fun newInstance(hour: Int, minute: Int,viewId:Int): TimePickerFragment {
            return TimePickerFragment().apply {
                arguments = Bundle().apply {
                    putInt(HOUR_TAG, hour)
                    putInt(MINUTE_TAG, minute)
                    putInt(TYPE_TAG,viewId)
                }
            }
        }
    }
}