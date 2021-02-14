package com.stasenkots.studentstimetable.ui.homework.archive


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.RecyclerView
import com.stasenkots.logic.entity.state.State
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.utils.MODE_MODERATOR
import com.stasenkots.logic.utils.parseToString
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.databinding.ItemHomeworkBinding
import com.stasenkots.studentstimetable.ui.homework.CONTEXTUAL_MODE
import com.stasenkots.studentstimetable.ui.homework.HomeworkActivity
import com.stasenkots.studentstimetable.ui.homework.NORMAL_MODE


class HomeworksArchiveAdapter(private var states: List<State>) :
    RecyclerView.Adapter<HomeworksArchiveAdapter.HomeworksArchiveViewHolder>() {
    private var mode = NORMAL_MODE
    private var actionMode: ActionMode? = null
    private var showedStates:List<State>
    init {
        states=states.sortedByDescending { it.date }
        showedStates=states
    }
    inner class HomeworksArchiveViewHolder(val binding: ItemHomeworkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isSelected = false
        fun bind(state: State) {
            with(binding) {
                root.setBackgroundColor(
                    itemView.context.resources.getColor(
                        R.color.cardview_background,
                        itemView.context.theme
                    )
                )
                this.date.text = state.date.parseToString()
                homework.text =
                    if (state.homework.isEmpty()) itemView.context.getString(R.string.no_homework)
                    else state.homework
                if (state.comment.isEmpty()) comment.visibility = View.GONE
                else {
                    comment.visibility = View.VISIBLE
                    comment.text = state.comment
                }
                if (state.absentUsers.contains(User.id)) {
                    pass.visibility = View.VISIBLE
                    pass.text = itemView.context.getText(R.string.pass)
                } else pass.visibility = View.GONE
                if (User.mode== MODE_MODERATOR){
                   selectItems(root,state)
                }
            }
        }
            private fun selectItems(root:View, state: State){
                itemView.setOnLongClickListener {
                    val activity = itemView.context as HomeworkActivity
                    actionMode = activity.startSupportActionMode(activity.callback)
                    mode = CONTEXTUAL_MODE
                    root.setBackgroundColor(
                        activity.getColor(
                            R.color.light_grey
                        )
                    )
                    isSelected = true
                    activity.viewModel.selectedItems.add(state.id)
                    actionMode?.title = String.format(
                        activity.getString(R.string.n_selected),
                        activity.viewModel.selectedItems.size
                    )
                    true
                }
                itemView.setOnClickListener {
                    if (mode == CONTEXTUAL_MODE) {
                        val activity = itemView.context as HomeworkActivity
                        if (isSelected) {
                            root.setBackgroundColor(
                                activity.resources.getColor(
                                    R.color.cardview_background,
                                    activity.theme
                                )
                            )
                            isSelected = false
                            activity.viewModel.selectedItems.remove(state.id)
                        } else {
                            root.setBackgroundColor(
                                activity.resources.getColor(
                                    R.color.light_grey,
                                    activity.theme
                                )
                            )
                            isSelected = true
                            activity.viewModel.selectedItems.add(state.id)
                        }
                        actionMode?.title = String.format(
                            activity.getString(R.string.n_selected),
                            activity.viewModel.selectedItems.size
                        )
                    }
                }
            }
    }

    private var _binding: ItemHomeworkBinding? = null
    private val binding get() = _binding!!

    fun filter(text: String) {
        showedStates = if (text.isEmpty()) states
        else{
            states.filter {
                it.homework.contains(text, true)
            }
        }
        notifyDataSetChanged()
    }

    fun update(mode: Int) {
        this.mode = mode
        if (mode == NORMAL_MODE) {
            notifyDataSetChanged()
        }
    }

    fun update(list: List<State>) {
        states = list.sortedByDescending { it.date }
        showedStates=states
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeworksArchiveViewHolder {
        _binding = ItemHomeworkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeworksArchiveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeworksArchiveViewHolder, position: Int) {
        holder.bind(showedStates[position])
    }


    override fun getItemCount() = showedStates.size
}