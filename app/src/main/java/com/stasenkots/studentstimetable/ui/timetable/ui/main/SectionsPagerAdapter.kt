package com.stasenkots.studentstimetable.ui.timetable.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.stasenkots.studentstimetable.R


class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    private val tabTitles = arrayOf(
        R.string.monday, R.string.tuesday, R.string.wednesday, R.string.thursday,
        R.string.friday, R.string.saturday, R.string.sunday
    )

    override fun getItem(position: Int): Fragment {
        return PlaceholderFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence{
        return context.resources.getString(tabTitles[position])
    }

    override fun getCount() = tabTitles.size

    fun update(){
        notifyDataSetChanged()
    }

}