package com.stasenkots.studentstimetable.ui.setup.start_of_sem_picker

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.databinding.FragmentCreateGroupBinding
import com.stasenkots.studentstimetable.databinding.SemStartFragmentBinding
import java.time.LocalDate

class SemStartFragment : Fragment() {

    private var _binding: SemStartFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SemStartFragment()
    }

    private val viewModel: SemStartViewModel by lazy {
        ViewModelProvider(this).get(SemStartViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SemStartFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonContinueClickListener()
    }
    private fun setButtonContinueClickListener(){
        binding.buttonContinue.setOnClickListener {
            val day = binding.calender.dayOfMonth
            val month = binding.calender.month + 1
            val year = binding.calender.year
            val date = LocalDate.of(year, month, day)
            viewModel.setStartOfSemesters(date)
            findNavController().navigate(R.id.show_create_group_fragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}