package com.stasenkots.studentstimetable.ui.timetable.ui.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.stasenkots.studentstimetable.databinding.FragmentTimeTableBinding
import com.stasenkots.studentstimetable.ui.timetable.TimeTableActivity
import com.stasenkots.studentstimetable.ui.timetable.TimeTableViewModel
import com.stasenkots.studentstimetable.ui.timetable.dialogs.datepicker.DatePickerViewModel
import java.time.LocalDate


class PlaceholderFragment : Fragment() {
    private var _binding: FragmentTimeTableBinding? = null
    private val binding get() = _binding!!
    private val pageViewModel by lazy { ViewModelProvider(this).get(PageViewModel::class.java) }
    private val timeTableViewModel by lazy { ViewModelProvider(requireActivity()).get(TimeTableViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(pageViewModel) {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?:1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimeTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recycleViewTimeTable.layoutManager = LinearLayoutManager(context)
        binding.recycleViewTimeTable.adapter = TimeTableAdapter(emptyList())
        pageViewModel.setDate(timeTableViewModel.selectedDate)
        timeTableViewModel.isDataLoaded.observe(viewLifecycleOwner,{
            pageViewModel.getLessons()
            pageViewModel.setLiveQuery(viewLifecycleOwner)
        })
        pageViewModel.lessons.observe(viewLifecycleOwner, { lessonItems ->
                val adapter = binding.recycleViewTimeTable.adapter as TimeTableAdapter
                adapter.update(lessonItems)
                if (lessonItems.isEmpty()) {
                    binding.textViewNoLessons.visibility = View.VISIBLE
                } else {
                    binding.textViewNoLessons.visibility = View.GONE
                }

            })
        val datePickerViewModel =
            ViewModelProvider(requireActivity()).get(DatePickerViewModel::class.java)
        datePickerViewModel.currentDate.observe(viewLifecycleOwner, {
            pageViewModel.setDate(it)
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"


        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }


}