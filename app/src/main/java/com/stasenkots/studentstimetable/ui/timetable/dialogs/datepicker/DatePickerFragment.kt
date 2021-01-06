package com.stasenkots.studentstimetable.ui.timetable.dialogs.datepicker

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import java.time.LocalDate
import java.util.*

class DatePickerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity()).get(DatePickerViewModel::class.java)
        val date = viewModel.currentDate.value?: LocalDate.now()
        //TODO set date with count of tabs pos
        return DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                viewModel.setCurrentDate(LocalDate.of(year,month+1, dayOfMonth))
            },
            date.year,
            date.month.value-1,
            date.dayOfMonth
        )
    }
}