package com.stasenkots.studentstimetable.ui.timetable.dialogs.detele_dialog

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.stasenkots.studentstimetable.R
private const val LESSON_ID="lesson id"
class DeleteDialogFragment : DialogFragment() {
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(DeleteDialogViewModel::class.java) }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val lessonId=arguments?.getString(LESSON_ID).orEmpty()
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.Deletion))
            .setMessage(getString(R.string.do_you_want_delete))
            .setNeutralButton(getString(R.string.cancel)) { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
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