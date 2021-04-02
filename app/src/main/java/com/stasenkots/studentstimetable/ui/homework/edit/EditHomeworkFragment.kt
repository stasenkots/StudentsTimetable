package com.stasenkots.studentstimetable.ui.homework.edit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.stasenkots.logic.utils.parseToString
import com.stasenkots.logic.utils.toLocalDate
import com.stasenkots.logic.utils.toLong
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.databinding.EditHomeworkFragmentBinding
import com.stasenkots.studentstimetable.ui.homework.dialog.HomeworkDatesFragment
import com.stasenkots.studentstimetable.ui.homework.dialog.HomeworkDatesViewModel
import java.time.LocalDate
import java.util.*

private const val TAG_SUBJECT_ID = "lesson id"
private const val TAG_CURRENT_DATE = "current date"
private const val TAG_ALERT_DIALOG = "AlertDialog"

class EditHomeworkFragment : Fragment() {

    companion object {
        fun newInstance(subjectId: String?, date: Long) = EditHomeworkFragment().apply {
            arguments = Bundle().apply {
                putString(TAG_SUBJECT_ID, subjectId)
                putLong(TAG_CURRENT_DATE, date)
            }
        }
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            EditHomeworkViewModelFactory(requireActivity().application)
        ).get(EditHomeworkViewModel::class.java)
    }
    private val homeworkDatesViewModel by lazy {
        ViewModelProvider(requireActivity()).get(
            HomeworkDatesViewModel::class.java
        )
    }
    private var _binding: EditHomeworkFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditHomeworkFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val subjectId = arguments?.getString(TAG_SUBJECT_ID).orEmpty()
        val currentDate = arguments?.getLong(TAG_CURRENT_DATE) ?: LocalDate.now().toLong()
        viewModel.init(subjectId, currentDate.toLocalDate())
        bind()
        binding.buttonSave.setOnClickListener {
            buttonSaveListener()
        }
        binding.day.setOnClickListener {
            dayListener()
        }
    }

    private fun bind() {
        with(viewModel.state) {
            binding.homework.setText(homework)
            binding.comment.setText(comment)
            binding.day.text = date.parseToString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun buttonSaveListener() {
        with(binding) {
            viewModel.sendState(
                homework.text.toString(),
                comment.text.toString()
            )
            progressBar.visibility = View.VISIBLE
        }
        viewModel.errorBus.observe(viewLifecycleOwner, { result ->
            binding.progressBar.visibility = View.GONE
            if (result == null) {
                Toast.makeText(context, R.string.saved, Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            } else {
                Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun dayListener() {
        HomeworkDatesFragment.newInstance(viewModel.state.subject, viewModel.date.toLong())
            .show(requireActivity().supportFragmentManager, TAG_ALERT_DIALOG)
        homeworkDatesViewModel.day.observe(viewLifecycleOwner, { date ->
            binding.day.text = date.parseToString()
            viewModel.reinit(viewModel.state.subject, date)
            bind()
        })
    }

}