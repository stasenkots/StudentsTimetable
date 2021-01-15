package com.stasenkots.studentstimetable.ui.timetable

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.*
import com.google.android.material.tabs.TabLayout
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.utils.MODE_STUDENT
import com.stasenkots.logic.utils.launchUI
import com.stasenkots.logic.utils.toLong
import com.stasenkots.studentstimetable.ADS_ID
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.constants.ActionsConstants.ACTION_DELETE_LESSON
import com.stasenkots.studentstimetable.constants.MainActivityConstants.CURRENT_DATE_TAG
import com.stasenkots.studentstimetable.constants.MainActivityConstants.DELETE_LESSON_TAG
import com.stasenkots.studentstimetable.constants.MainActivityConstants.LESSON_ID_TAG
import com.stasenkots.studentstimetable.databinding.ActivityTimeTableBinding
import com.stasenkots.studentstimetable.ui.timetable.dialogs.datepicker.DatePickerFragment
import com.stasenkots.studentstimetable.ui.timetable.dialogs.datepicker.DatePickerViewModel
import com.stasenkots.studentstimetable.ui.timetable.dialogs.detele_dialog.DeleteDialogFragment
import com.stasenkots.studentstimetable.ui.timetable.dialogs.detele_dialog.DeleteDialogViewModel
import com.stasenkots.studentstimetable.ui.timetable.dialogs.lesson_item_action.*

import com.stasenkots.studentstimetable.ui.timetable.ui.main.SectionsPagerAdapter
import java.time.LocalDate
import java.util.*
import kotlin.concurrent.schedule

private const val DATE_PICKER_TAG = "Date Picker"

class TimeTableActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimeTableBinding
    private lateinit var mInterstitialAd: InterstitialAd
    private val lessonItemActionViewModel by lazy {
        ViewModelProvider(this).get(
            LessonItemActionViewModel::class.java
        )
    }
    private val viewModel by lazy { ViewModelProvider(this).get(TimeTableViewModel::class.java) }
    private val datePickerViewModel by lazy { ViewModelProvider(this).get(DatePickerViewModel::class.java) }
    private val deleteDialogViewModel by lazy { ViewModelProvider(this).get(DeleteDialogViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeTableBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initAd()
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(binding.viewPager)
        binding.tabs.tabMode = TabLayout.MODE_SCROLLABLE
        if (User.mode == MODE_STUDENT) binding.toolbar.menu.clear()
        else setToolbarListener()
        datePickerViewModel.currentDate.observe(this, { date ->
            binding.viewPager.currentItem = date.dayOfWeek.value - 1
            viewModel.selectedDate = date
        })
        binding.buttonCalendar.setOnClickListener {
            DatePickerFragment().show(supportFragmentManager, DATE_PICKER_TAG)
        }
        observeLessonItemAction()
        viewModel.isDataLoaded.observe(this, {
            binding.progressBar.visibility = View.GONE
        })
        viewModel.errorBus.observe(this, {
            binding.progressBar.visibility = View.GONE
            Toast.makeText(this, R.string.some_data_non_actual, Toast.LENGTH_LONG).show()
        })

    }

    private fun initAd() {
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = ADS_ID
        mInterstitialAd.loadAd(
            AdRequest.Builder()
                .build()
        )
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdFailedToLoad(error: LoadAdError?) {
                FirebaseCrashlytics.getInstance().log(error?.message.orEmpty())
            }
            override fun onAdLoaded() {
                mInterstitialAd.show()
            }
        }

    }

    private fun observeLessonItemAction() {
        lessonItemActionViewModel.lessonItemAction.observe(this, { action ->
            val bundle = Bundle().apply {
                putString(LESSON_ID_TAG, lessonItemActionViewModel.lessonId)
                putLong(
                    CURRENT_DATE_TAG,
                    lessonItemActionViewModel.date ?: LocalDate.now().toLong()
                )
            }
            val intent = Intent(action).apply {
                putExtras(bundle)
            }
            val timeTableActivityInfo = packageManager.getActivityInfo(componentName, 0)
            val resolvedActivityInfo =
                packageManager.queryIntentActivities(intent, 0)[0].activityInfo
            if (resolvedActivityInfo.name == timeTableActivityInfo.name) {
                doAction(action)
            } else {
                startActivity(intent)
            }

        })
    }

    private fun doAction(action: String) {
        when (action) {
            ACTION_DELETE_LESSON -> deleteLesson()
        }
    }

    private fun deleteLesson() {
        lessonItemActionViewModel.lessonId?.let {
            DeleteDialogFragment.newInstance(it).show(
                supportFragmentManager,
                DELETE_LESSON_TAG
            )
        }
        deleteDialogViewModel.status.observe(this, { status ->
            if (status == null) {
                Toast.makeText(this, getString(R.string.deleted), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.no_internet_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setToolbarListener() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.add -> {
                    lessonItemActionViewModel.setLessonItemAction(R.string.add)
                    true
                }
                else -> false
            }
        }
    }


}
