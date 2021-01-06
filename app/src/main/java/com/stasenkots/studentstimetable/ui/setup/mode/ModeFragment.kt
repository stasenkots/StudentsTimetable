package com.stasenkots.studentstimetable.ui.setup.mode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.stasenkots.logic.utils.MODE_MODERATOR
import com.stasenkots.logic.utils.MODE_STUDENT
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.databinding.FragmentModeBinding


class ModeFragment : Fragment() {
    private var _binding: FragmentModeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { ViewModelProvider(this).get(ModeViewModel::class.java) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonModeratorMode.setOnClickListener {
            if (!setName())return@setOnClickListener
            viewModel.setUserMode(MODE_MODERATOR)
            findNavController().navigate(R.id.show_registration_fragment)
        }
        binding.buttonStudentMode.setOnClickListener {
            if (!setName())return@setOnClickListener
            viewModel.setUserMode(MODE_STUDENT)
            findNavController().navigate(R.id.show_registration_fragment)

        }
        binding.textInputName.doOnTextChanged { text, start, before, count ->
            binding.textInputLayoutName.error=null
        }

    }
    private fun setName():Boolean{
        if (binding.textInputName.text.isNullOrBlank()){
            binding.textInputLayoutName.error=getString(R.string.name_cannot_be_empty)
            return false
        }
        viewModel.setUserName(binding.textInputName.text.toString())
        return true
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}