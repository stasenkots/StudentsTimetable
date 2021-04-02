package com.stasenkots.studentstimetable

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import java.net.UnknownHostException

class Analytics(app: Context) {
    private val analytics = (app as App).analytics
    private val crashlytics = (app as App).crashlytics

     fun log(event:String,message:String){
        analytics.logEvent(event, Bundle().apply {
            putString("Event",message)
        })
    }

    fun logError(e:Throwable){
        if (e !is UnknownHostException) {
            crashlytics.recordException(e)
            crashlytics.sendUnsentReports()
        }
    }
}