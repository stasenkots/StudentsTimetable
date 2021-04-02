package com.stasenkots.studentstimetable.ui.homework.archive

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.databinding.HomeworksArchiveFragmentBinding
import com.stasenkots.studentstimetable.ui.homework.HomeworkViewModel
import com.stasenkots.studentstimetable.ui.homework.HomeworkArchiveViewModelFactory

private const val TAG_SUBJECT_ID = "subject id"

class HomeworksArchiveFragment : Fragment() {


    private val viewModel by lazy { ViewModelProvider(this).get(HomeworksArchiveViewModel::class.java) }
    private val homeworkViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            HomeworkArchiveViewModelFactory(requireActivity().application)
        ).get(
            HomeworkViewModel::class.java
        )
    }
    private var _binding: HomeworksArchiveFragmentBinding? = null
    val subjectId by lazy { arguments?.getString(TAG_SUBJECT_ID).orEmpty() }
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeworksArchiveFragmentBinding.inflate(inflater, container, false)
        viewModel.getStates(subjectId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHomeworksView()
        setupErrorBus()
        setSearchView()
    }

    private fun setupHomeworksView() {
        binding.homeworks.apply {
            layoutManager = LinearLayoutManager(context)
            val adapter = HomeworksArchiveAdapter(viewModel.states)
            hasFixedSize()
            homeworkViewModel.mode.observe(viewLifecycleOwner, { mode ->
                adapter.update(mode)
            })
            this.adapter = adapter
        }
    }

    private fun setupErrorBus() {
        homeworkViewModel.errorBus.observe(viewLifecycleOwner, { status ->
            if (status == null) {
                viewModel.getStates(subjectId)
                (binding.homeworks.adapter as HomeworksArchiveAdapter).update(viewModel.states)
                Toast.makeText(context, R.string.deleted, Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT)
                    .show()

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(subjectId: String?) =
            HomeworksArchiveFragment().apply {
                arguments = Bundle().apply {
                    putString(TAG_SUBJECT_ID, subjectId)
                }
            }
    }

    private fun setSearchView() {
        val searchItem = binding.toolbar.menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView
        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    (binding.homeworks.adapter as HomeworksArchiveAdapter)
                        .filter(newText.toString())
                    return true
                }
            })
        }
    }


}