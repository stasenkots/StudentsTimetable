package com.stasenkots.studentstimetable.ui.student.absent_students

import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.util.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.stasenkots.logic.utils.toLocalDate
import com.stasenkots.logic.utils.toLong
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.databinding.AbsentStudentsFragmentBinding
import java.time.LocalDate


private const val TAG_SUBJECT_ID = "subject id"
private const val TAG_DATE = "date"

class AbsentStudentsFragment : Fragment() {
    private var _binding: AbsentStudentsFragmentBinding? = null
    private val binding
        get() = _binding!!

    companion object {
        fun newInstance(subjectId: String?, date: Long?) = AbsentStudentsFragment().apply {
            arguments = Bundle().apply {
                putString(TAG_SUBJECT_ID, subjectId)
                putLong(TAG_DATE, date ?: 0)
            }
        }
    }

    private val viewModel by lazy { ViewModelProvider(this).get(AbsentStudentsViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AbsentStudentsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val subjectId = arguments?.getString(TAG_SUBJECT_ID).orEmpty()
        val currentDate = arguments?.getLong(TAG_DATE) ?: LocalDate.now().toLong()
        viewModel.init(subjectId, currentDate.toLocalDate())
        setToolbarListener()
        bind()
        viewModel.errorBus.observe(viewLifecycleOwner, {
            if (it == null) {
                Toast.makeText(context, R.string.saved, Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            } else {
                Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun bind() {
        with(binding.listView) {
            choiceMode = ListView.CHOICE_MODE_MULTIPLE
            binding.progressBar.visibility=View.GONE
            adapter =
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_multiple_choice,
                    viewModel.students.map { it.name }
                )
            viewModel.checkedItemPositions.forEach { key, value ->
                setItemChecked(key,value)
            }
        }
    }
    private fun setToolbarListener() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.save -> {
                    binding.progressBar.visibility= View.VISIBLE
                    viewModel.save(binding.listView.checkedItemPositions)
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}