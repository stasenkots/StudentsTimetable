package com.stasenkots.studentstimetable.ui.lesson.edit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.stasenkots.logic.entity.LessonItem
import com.stasenkots.logic.utils.parseAsTime
import com.stasenkots.logic.utils.parseTime
import com.stasenkots.logic.utils.toLong
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.databinding.EditLessonFragmentBinding
import com.stasenkots.studentstimetable.convertToString

import com.stasenkots.studentstimetable.ui.timetable.dialogs.day_of_week_picker.DayOfWeekPickerFragment
import com.stasenkots.studentstimetable.ui.timetable.dialogs.day_of_week_picker.DayOfWeekPickerViewModel
import com.stasenkots.studentstimetable.ui.timetable.dialogs.timepicker.TimePickerFragment
import com.stasenkots.studentstimetable.ui.timetable.dialogs.timepicker.TimePickerViewModel
import com.stasenkots.studentstimetable.toDayOfWeek
import java.time.LocalDate

private const val LESSON_ID_TAG = "lesson id"
private const val TAG_DATE = "date"
private const val TIME_PICKER_TAG = "time picker tag"
private const val DAY_OF_WEEK_PICKER_TAG = "day of week picker tag"

class EditLessonFragment : Fragment() {
    private var _binding: EditLessonFragmentBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            EditLessonViewModelFactory(requireActivity().application)
        ).get(EditLessonViewModel::class.java)
    }
    private val timePickerViewModel by lazy {
        ViewModelProvider(requireActivity()).get(
            TimePickerViewModel::class.java
        )
    }
    private val dayOfWeekPickerViewModel by lazy {
        ViewModelProvider(requireActivity()).get(
            DayOfWeekPickerViewModel::class.java
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditLessonFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val lessonId = arguments?.getString(LESSON_ID_TAG)
        viewModel.getLessonItem(lessonId)
        bind(viewModel.lessonItem)
        setupToolbar()
        setTimeClickListener(binding.startLesson)
        setTimeClickListener(binding.endLesson)
        setDayOfWeekClickListener()
        observeStatus()

    }

    private fun observeStatus() {
        viewModel.status.observe(viewLifecycleOwner, { status ->
            if (status is Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(
                    context,
                    getString(R.string.no_internet_connection),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(context, getString(R.string.saved), Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            }
        })
    }

    private fun setDayOfWeekClickListener() {
        binding.day.setOnClickListener {
            val mDate = binding.day.text.toString().toDayOfWeek(requireContext())
            DayOfWeekPickerFragment.newInstance(mDate).show(
                requireActivity().supportFragmentManager, DAY_OF_WEEK_PICKER_TAG
            )
        }
        dayOfWeekPickerViewModel.dayOfWeek.observe(viewLifecycleOwner, {
            binding.day.text = it
        })
    }

    private fun setupToolbar() {
        binding.toolbar.menu.getItem(0).setOnMenuItemClickListener {
            binding.progressBar.visibility = View.VISIBLE
            if (!validFields()) {
                binding.progressBar.visibility = View.GONE
                return@setOnMenuItemClickListener true
            }
            val item = createLessonItem()
            viewModel.lessonItem = item
            viewModel.sendData(item)
            return@setOnMenuItemClickListener true
        }
    }

    private fun createLessonItem(): LessonItem {
        with(binding) {
            return LessonItem(
                lesson = viewModel.lessonItem.lesson,
                subject = viewModel.lessonItem.subject,
                name = name.text.toString(),
                type = type.text.toString(),
                subgroup = subgroup.text.toString(),
                timeStart = startLesson.text.toString(),
                timeEnd = endLesson.text.toString(),
                room = room.text.toString(),
                dayOfWeek = day.text.toString().toDayOfWeek(requireContext()),
                teacher = teacherName.text.toString(),
                state = viewModel.lessonItem.state,
                date = viewModel.lessonItem.date,
                link = link.text.toString()
            )

        }
    }

    private fun setTimeClickListener(view: TextView) {
        view.setOnClickListener {
            val time =
                if (view.text.isNullOrEmpty()) getString(R.string.defalt_time_start).parseTime()
                else view.text.toString().parseTime()
            TimePickerFragment.newInstance(time.first, time.second, view.id).show(
                requireActivity().supportFragmentManager,
                TIME_PICKER_TAG
            )
        }
        timePickerViewModel.time.observe(viewLifecycleOwner, {
            if (timePickerViewModel.viewId == view.id) {
                view.text = it.parseAsTime()
            }
        })
    }

    private fun bind(lessonItem: LessonItem) {
        bindName(lessonItem)
        bindTeacherName(lessonItem)
        bindRoom(lessonItem)
        bindSubgroup(lessonItem)
        bindStartTime(lessonItem)
        bindEndTime(lessonItem)
        bindType(lessonItem)
        bindDay(lessonItem)
        bindLink(lessonItem)

    }

    private fun bindLink(lessonItem: LessonItem) {
        binding.link.setText(lessonItem.link)
    }

    private fun bindDay(lessonItem: LessonItem) {
        binding.day.text = lessonItem.dayOfWeek.convertToString(requireContext())
    }

    private fun bindType(lessonItem: LessonItem) {
        with(binding) {
            type.setText(lessonItem.type)
            type.doOnTextChanged { _, _, _, _ ->
                layoutType.error = null
            }
            type.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    viewModel.subjectType
                )
            )
        }
    }

    private fun bindEndTime(lessonItem: LessonItem) {
        with(binding) {
            endLesson.text =
                if (lessonItem.timeEnd.isEmpty()) getString(R.string.defalt_time_end)
                else lessonItem.timeEnd
        }
    }

    private fun bindStartTime(lessonItem: LessonItem) {
        with(binding) {
            startLesson.text =
                if (lessonItem.timeStart.isEmpty()) getString(R.string.defalt_time_start)
                else lessonItem.timeStart
        }
    }

    private fun bindName(lessonItem: LessonItem) {
        with(binding) {
            name.setText(lessonItem.name)
            name.doOnTextChanged { _, _, _, _ ->
                layoutName.error = null
            }
            name.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    viewModel.subjectNames
                )
            )
        }
    }

    private fun bindTeacherName(lessonItem: LessonItem) {
        with(binding) {
            teacherName.setText(lessonItem.teacher)
            teacherName.doOnTextChanged { _, _, _, _ ->
                layoutTeacherName.error = null
            }
            teacherName.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    viewModel.subjectTeachers
                )
            )
        }
    }

    private fun bindRoom(lessonItem: LessonItem) {
        with(binding) {
            room.setText(lessonItem.room)
            room.doOnTextChanged { _, _, _, _ ->
                layoutRoom.error = null
            }
        }
    }

    private fun bindSubgroup(lessonItem: LessonItem) {
        with(binding) {
            subgroup.setText(lessonItem.subgroup)
            subgroup.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    viewModel.subjectSubgroups
                )
            )
        }
    }

    private fun validFields(): Boolean {
        with(binding) {
            val fields = mapOf(
                name to layoutName,
                teacherName to layoutTeacherName,
                room to layoutRoom,
                type to layoutType
            )
            for (field in fields) {
                if (field.key.text.isNullOrEmpty()) {
                    field.value.error = getString(R.string.this_field_cannot_be_empty)
                    return false
                }
            }
            return true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(lessonId: String?, date: Long?): EditLessonFragment {
            return EditLessonFragment().apply {
                arguments = Bundle().also {
                    it.putString(LESSON_ID_TAG, lessonId)
                    it.putLong(TAG_DATE, date ?: LocalDate.now().toLong())
                }
            }
        }
    }

}