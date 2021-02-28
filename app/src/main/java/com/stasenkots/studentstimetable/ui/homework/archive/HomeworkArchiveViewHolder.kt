package com.stasenkots.studentstimetable.ui.homework.archive

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.entity.state.State
import com.stasenkots.logic.utils.MODE_MODERATOR
import com.stasenkots.logic.utils.parseToString
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.databinding.ItemHomeworkBinding
import com.stasenkots.studentstimetable.ui.homework.CONTEXTUAL_MODE
import com.stasenkots.studentstimetable.ui.homework.HomeworkActivity

class HomeworksArchiveViewHolder(
    val binding: ItemHomeworkBinding,
    private val adapter: HomeworksArchiveAdapter
) :
    RecyclerView.ViewHolder(binding.root) {
    private var isSelected = false

    fun bind(state: State) {
        setBackgroundColor()
        bindDate(state)
        bindHomework(state)
        bindComment(state)
        bindAbsentUsers(state)
        if (User.mode == MODE_MODERATOR) {
            selectItems(state)
        }

    }

    private fun bindDate(state: State) {
        binding.date.text = state.date.parseToString()
    }

    private fun bindHomework(state: State) {
        binding.homework.text =
            if (state.homework.isEmpty()) itemView.context.getString(R.string.no_homework)
            else state.homework
    }

    private fun bindComment(state: State) {
        if (state.comment.isEmpty()) binding.comment.visibility = View.GONE
        else {
            binding.comment.visibility = View.VISIBLE
            binding.comment.text = state.comment
        }
    }

    private fun bindAbsentUsers(state: State) {
        if (state.absentUsers.contains(User.id)) {
            binding.pass.visibility = View.VISIBLE
            binding.pass.text = itemView.context.getText(R.string.pass)
        } else binding.pass.visibility = View.GONE
    }

    private fun setBackgroundColor() {
        binding.root.setBackgroundColor(
            itemView.context.resources.getColor(
                R.color.cardview_background,
                itemView.context.theme
            )
        )
    }

    private fun selectItems(state: State) {
        setOnLongClickListener(state)
        setOnClickListener(state)
    }

    private fun setOnLongClickListener(state: State) {
        itemView.setOnLongClickListener {
            val activity = itemView.context as HomeworkActivity
            adapter.actionMode = activity.startSupportActionMode(activity.callback)
            binding.root.setBackgroundColor(
                activity.getColor(
                    R.color.light_grey
                )
            )
            isSelected = true
            activity.viewModel.selectedItems.add(state.id)
            adapter.actionMode?.title = String.format(
                activity.getString(R.string.n_selected),
                activity.viewModel.selectedItems.size
            )
            true
        }
    }

    private fun setOnClickListener(state: State) {
        val activity = itemView.context as HomeworkActivity
        itemView.setOnClickListener {
            if (adapter.mode == CONTEXTUAL_MODE) {
                doOnContextualMode(activity, state)
                adapter.actionMode?.title = String.format(
                    activity.getString(R.string.n_selected),
                    activity.viewModel.selectedItems.size
                )
            }

        }
    }

    private fun doOnContextualMode(activity: HomeworkActivity, state: State) {
        if (isSelected) {
            binding.root.setBackgroundColor(
                activity.resources.getColor(
                    R.color.cardview_background,
                    activity.theme
                )
            )
            isSelected = false
            activity.viewModel.selectedItems.remove(state.id)
        } else {
            binding.root.setBackgroundColor(
                activity.resources.getColor(
                    R.color.light_grey,
                    activity.theme
                )
            )
            isSelected = true
            activity.viewModel.selectedItems.add(state.id)
        }
    }


}