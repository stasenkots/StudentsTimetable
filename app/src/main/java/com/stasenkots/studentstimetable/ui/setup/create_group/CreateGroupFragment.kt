package com.stasenkots.studentstimetable.ui.setup.create_group

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.stasenkots.logic.entity.User
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.databinding.FragmentCreateGroupBinding
import com.stasenkots.studentstimetable.showError
import com.stasenkots.studentstimetable.ui.timetable.TimeTableActivity

class CreateGroupFragment : Fragment() {
    private var _binding: FragmentCreateGroupBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CreateGroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateGroupViewModel::class.java)
        viewModel.setGroupId()
        binding.textViewGroupId.text = User.groupId
        binding.textViewGroupId.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, User.groupId)
                type = "text/plain"

            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        binding.buttonContinue.setOnClickListener {
            viewModel.save()
        }
        viewModel.isSaved.observe(viewLifecycleOwner, { _ ->
            startActivity(Intent(context, TimeTableActivity::class.java))
            activity?.finish()
        })
        viewModel.errorBus.observe(viewLifecycleOwner, {
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show()
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}