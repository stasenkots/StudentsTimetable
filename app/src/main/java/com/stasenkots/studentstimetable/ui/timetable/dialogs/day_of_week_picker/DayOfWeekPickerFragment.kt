package com.stasenkots.studentstimetable.ui.timetable.dialogs.day_of_week_picker

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.stasenkots.logic.entity.DayOfWeek
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.databinding.CheckboxDayOfWeekPickerBinding
import com.stasenkots.studentstimetable.databinding.TitleDayOfWeekPickerBinding

private const val DAY_OF_WEEK = "day of week"
private const val WARNING = "warning"
private const val WEEK = "week"

class DayOfWeekPickerFragment : DialogFragment() {
    private val titleBinding by lazy {
        TitleDayOfWeekPickerBinding.inflate(
            LayoutInflater.from(
                context
            )
        )
    }
    private val checkBoxBinding by lazy {
        CheckboxDayOfWeekPickerBinding.inflate(
            LayoutInflater.from(
                context
            )
        )
    }
    val viewModel by lazy { ViewModelProvider(requireActivity()).get(DayOfWeekPickerViewModel::class.java) }
    private val items by lazy {
        arrayOf(
            getString(R.string.monday),
            getString(R.string.tuesday),
            getString(R.string.wednesday),
            getString(R.string.thursday),
            getString(R.string.friday),
            getString(R.string.saturday),
            getString(R.string.sunday)
        )
    }

    companion object {
        fun newInstance(dayOfWeek: DayOfWeek) =
            DayOfWeekPickerFragment().apply {
                arguments = Bundle().apply {
                    putInt(DAY_OF_WEEK, dayOfWeek.dayOfWeek)
                    putInt(WEEK, dayOfWeek.week)
                }
            }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dayOfWeek = DayOfWeek(
            arguments?.getInt(DAY_OF_WEEK) ?: 0,
            arguments?.getInt(WEEK) ?: 0
        )
        titleBinding.title.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.weeks_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        checkBoxBinding.checkbox.isChecked = viewModel.isCheckedCheckBox
        checkBoxBinding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            checkBoxBinding.checkbox.isChecked = isChecked
            viewModel.isCheckedCheckBox=isChecked
        }
        var selectedItem = dayOfWeek.dayOfWeek-1
        return MaterialAlertDialogBuilder(requireContext())
            .setCustomTitle(titleBinding.root.apply {})
            .setView(checkBoxBinding.root)
            .setPositiveButton(R.string.ok) { _, _ ->
                val week =
                    if (checkBoxBinding.checkbox.isChecked) getString(R.string.every_week)
                    else titleBinding.title.selectedItem.toString()
                val day = items[selectedItem]
                WarningDialogFragment.newInstance(day, week).show(
                    requireActivity().supportFragmentManager,
                    WARNING
                )
                dialog?.dismiss()
            }
            .setSingleChoiceItems(items, selectedItem) { _, which ->
                selectedItem = which
            }
            .create()

    }

}