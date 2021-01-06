package com.stasenkots.studentstimetable.ui.timetable.dialogs.day_of_week_picker

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.stasenkots.logic.utils.TAG
import com.stasenkots.logic.utils.toLocalDate
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.databinding.CheckboxDayOfWeekPickerBinding
import com.stasenkots.studentstimetable.databinding.TitleDayOfWeekPickerBinding
import java.time.LocalDate

private const val TAG_DATE="date"
private const val TAG_WARNING="warning"
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
        fun newInstance(date: Long) =
            DayOfWeekPickerFragment().apply {
                arguments = Bundle().apply {
                    putLong(TAG_DATE, date)
                }
            }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        titleBinding.title.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.weeks_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            titleBinding.title.adapter = adapter
        }
        checkBoxBinding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            checkBoxBinding.checkbox.isChecked = isChecked
        }
        val date=arguments?.getLong(TAG_DATE)?.toLocalDate()
        var selectedItem = date?.dayOfWeek?.value?.minus(1)?:0
        return MaterialAlertDialogBuilder(requireContext())
            .setCustomTitle(titleBinding.root)
            .setView(checkBoxBinding.root)
            .setPositiveButton(R.string.ok) { dialogInterface, id ->
                val week =
                    if (checkBoxBinding.checkbox.isChecked) getString(R.string.every_week)
                    else titleBinding.title.selectedItem.toString()
                val day = items[selectedItem]
                WarningDialogFragment.newInstance(day, week).show(requireActivity().supportFragmentManager,
                    TAG_WARNING)
                dialog?.dismiss()
            }
            .setSingleChoiceItems(items, selectedItem) { dialog, which ->
                selectedItem = which
            }
            .create()

    }

}