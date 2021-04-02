package com.stasenkots.logic

import android.content.Context
import androidx.core.content.edit

private const val PREFERENCE_FILE_KEY = "preference_file_key"

class SharedPrefs(private val appContext: Context) {
    private val startSemDateKey = "startSemDateKey"
    fun saveStartDate(value: String) {
        appContext.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE).edit {
            putString(startSemDateKey, value)
        }
    }

    fun getStartSemDate(): String {
        return appContext.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
            .getString(startSemDateKey,"19700101")!!
    }
}