package com.stasenkots.studentstimetable.ui.homework.archive


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    RecyclerView.Adapter<HomeworksArchiveViewHolder>() {
    var mode = NORMAL_MODE
    private var showedStates: List<State>
    var actionMode: ActionMode? = null

    init {
        states = states.sortedByDescending { it.date }
        showedStates = states
    }


    private var _binding: ItemHomeworkBinding? = null
    private val binding get() = _binding!!

    fun filter(text: String) {
        showedStates = if (text.isEmpty()) states
        else {
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
        showedStates = states
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeworksArchiveViewHolder {
        _binding = ItemHomeworkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeworksArchiveViewHolder(binding,this)
    }

    override fun onBindViewHolder(holder: HomeworksArchiveViewHolder, position: Int) {
        holder.bind(showedStates[position])
    }


    override fun getItemCount() = showedStates.size
}