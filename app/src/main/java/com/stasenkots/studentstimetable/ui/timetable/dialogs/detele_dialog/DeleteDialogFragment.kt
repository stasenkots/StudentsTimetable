package com.stasenkots.studentstimetable.ui.timetable.dialogs.detele_dialog

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.stasenkots.studentstimetable.R

private const val LESSON_ID = "lesson id"

class DeleteDialogFragment : DialogFragment() {
    private val viewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            DeleteDialogViewModelFactory(requireActivity().application)
        ).get(DeleteDialogViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val lessonId = arguments?.getString(LESSON_ID).orEmpty()
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.Deletion))
            .setMessage(getString(R.string.do_you_want_delete))
            .setNeutralButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                viewModel.deleteLesson(lessonId)
            }
            .create()
    }
    companion object{
        fun newInstance(lessonId:String):DeleteDialogFragment{
            return DeleteDialogFragment().apply {
                arguments=Bundle().apply {
                    putString(LESSON_ID,lessonId)
                }
            }
        }

    }

}