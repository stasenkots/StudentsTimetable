package com.stasenkots.studentstimetable.ui.setup.registration

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.stasenkots.logic.entity.User

import com.stasenkots.logic.utils.MODE_STUDENT
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.databinding.FragmentRegistrationBinding
import com.stasenkots.studentstimetable.showError
import com.stasenkots.studentstimetable.ui.timetable.TimeTableActivity

private const val ID_LENGTH = 32

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { ViewModelProvider(this).get(RegistrationViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (User.mode == MODE_STUDENT) {
            binding.textViewCreateGroup.visibility = View.GONE
        }
        binding.buttonContinue.setOnClickListener { valid() }
        binding.textViewCreateGroup.setOnClickListener {
            navigateToCreateGroup()
        }
        setIsGroupExistsObserver()
        setErrorBusObserver()
        binding.textInputGroupId.doOnTextChanged { _, _, _, _ ->
            binding.textInputLayoutGroupId.error = null
        }


    }

    private fun setIsGroupExistsObserver() {
        viewModel.isGroupExist.observe(viewLifecycleOwner, { existence ->
            binding.registrationProgressBar.visibility = View.GONE
            when (existence) {
                true -> {
                    viewModel.saveUser(binding.textInputGroupId.text.toString())
                }
                false -> binding.textInputLayoutGroupId.error =
                    getString(R.string.group_with_given_id_doesnt_exist)
            }

        })
    }

    private fun setErrorBusObserver() {
        viewModel.errorBus.observe(viewLifecycleOwner, {
            if (it == null) {
                startActivity(Intent(context, TimeTableActivity::class.java))
                activity?.finish()
            } else {
                binding.registrationProgressBar.visibility = View.GONE
                Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validInput() =
        when {
            binding.textInputGroupId.text.isNullOrBlank() -> {
                binding.textInputLayoutGroupId.error = getString(R.string.id_cannot_be_empty)
                false
            }
            binding.textInputGroupId.text?.length != ID_LENGTH -> {
                binding.textInputLayoutGroupId.error = String.format(
                    getString(R.string.id_length_must_be),
                    ID_LENGTH
                )
                false
            }
            else -> true
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToCreateGroup() {
        findNavController().navigate(R.id.show_sem_start_fragment)
    }

    private fun valid() {
        if (!validInput()) return
        binding.registrationProgressBar.visibility = View.VISIBLE
        viewModel.checkGroupExistence(binding.textInputGroupId.text.toString())
    }

}