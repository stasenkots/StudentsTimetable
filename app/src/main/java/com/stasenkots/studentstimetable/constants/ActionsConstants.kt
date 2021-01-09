package com.stasenkots.studentstimetable.constants

import com.stasenkots.studentstimetable.R
object ActionsConstants {
    const val ACTION_EDIT_HOMEWORK =
        "com.stasenkots.studentstimetable.constants.ACTION_EDIT_HOMEWORK"
    const val ACTION_EDIT_LESSON = "com.stasenkots.studentstimetable.constants.ACTION_EDIT_LESSON"
    const val ACTION_DELETE_LESSON =
        "com.stasenkots.studentstimetable.constants.ACTION_DELETE_LESSON"
    const val ACTION_VIEW_HOMEWORKS_ARCHIVE =
        "com.stasenkots.studentstimetable.constants.ACTION_VIEW_HOMEWORKS_ARCHIVE"
    const val ACTION_VIEW_ABSENT_USERS =
        "com.stasenkots.studentstimetable.constants.ACTION_VIEW_ABSENT_USERS"
    val activitiesActions = mapOf(
        R.string.edit to ACTION_EDIT_LESSON,
        R.string.add to ACTION_EDIT_LESSON,
        R.string.add_homework to ACTION_EDIT_HOMEWORK,
        R.string.archive_homeworks to ACTION_VIEW_HOMEWORKS_ARCHIVE,
        R.string.delete to ACTION_DELETE_LESSON,
        R.string.list_of_passed_users to ACTION_VIEW_ABSENT_USERS
    )
}