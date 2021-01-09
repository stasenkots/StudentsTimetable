package com.stasenkots.studentstimetable.ui.timetable.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.stasenkots.logic.entity.LessonItem
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.utils.MODE_STUDENT
import com.stasenkots.logic.utils.toLong
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.constants.ActionsConstants.ACTION_VIEW_HOMEWORKS_ARCHIVE
import com.stasenkots.studentstimetable.constants.MainActivityConstants.LESSON_ID_TAG
import com.stasenkots.studentstimetable.constants.MainActivityConstants.LESSON_ITEM_ACTION_TAG
import com.stasenkots.studentstimetable.databinding.ItemLessonBinding
import com.stasenkots.studentstimetable.ui.timetable.dialogs.lesson_item_action.LessonItemActionFragment


class TimeTableAdapter(private var list: List<LessonItem>) :
    RecyclerView.Adapter<TimeTableAdapter.TimeTableViewHolder>() {


    inner class TimeTableViewHolder(
        private val binding: ItemLessonBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(lessonItem: LessonItem) {
            with(binding) {
                itemTime.text = String.format(
                    itemView.context.getString(R.string.time),
                    lessonItem.timeStart,
                    lessonItem.timeEnd
                )
                itemLessonName.text = lessonItem.name
                itemClassroom.text = lessonItem.room
                itemComment.text = lessonItem.state?.comment
                itemLessonType.text =
                    String.format(itemView.context.getString(R.string.brakets), lessonItem.type)
                itemTeacherName.text = lessonItem.teacher
                validField(lessonItem.state?.homework, itemHomework)
                validField(lessonItem.subgroup, itemSubgroup)
                if (lessonItem.state?.absentUsers?.contains(User.id) == true) {
                    itemPass.visibility = View.VISIBLE
                    itemPass.text = itemView.context.getString(R.string.pass)
                } else itemPass.visibility = View.GONE
                root.setOnClickListener {
                    if (User.mode == MODE_STUDENT) {
                        val intent = Intent(ACTION_VIEW_HOMEWORKS_ARCHIVE).apply {
                            this.putExtra(LESSON_ID_TAG, lessonItem.lesson)
                        }
                        itemView.context.startActivity(intent)
                    } else {
                        val activity = itemView.context as AppCompatActivity
                        LessonItemActionFragment.newInstance(
                            lessonItem.lesson,
                            lessonItem.date.toLong()
                        ).show(
                            activity.supportFragmentManager,
                            LESSON_ITEM_ACTION_TAG
                        )
                    }
                }
            }

        }

        private fun validField(data: String?, textView: TextView) {
            if (data.isNullOrEmpty()) textView.visibility = View.GONE
            else {
                textView.visibility = View.VISIBLE
                textView.text = data
            }
        }

    }

    fun update(list: List<LessonItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeTableViewHolder {
        val binding = ItemLessonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeTableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeTableViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}