package com.stasenkots.studentstimetable

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

import androidx.work.*
import com.google.android.gms.ads.MobileAds
import com.parse.Parse
import com.stasenkots.logic.SharedPrefs
import com.stasenkots.studentstimetable.constants.AppConstants.NOTIFICATION_CHANNEL_ID
import com.stasenkots.studentstimetable.workmanager.CheckForUpdatesWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit


private const val NOTIFICATION_CHANNEL_NAME = "Channel name"
private const val CHECK_UPDATES_WORK = "check updates work"

class App : Application() {
    val sharedPrefs by lazy { SharedPrefs(applicationContext) }
    override fun onCreate() {
        super.onCreate()
        initParse()
        initTimber()
        createNotificationChannel()
        createUpdatesWork()
        MobileAds.initialize(this)


    }

    private fun initParse() {
        Parse.initialize(
            Parse.Configuration.Builder(applicationContext)
                .applicationId(APPLICATION_ID)
                .clientKey(CLIENT_KEY)
                .server(BASE_URL)
                .build()
        )
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun createUpdatesWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val uploadWorkRequest =
            PeriodicWorkRequestBuilder<CheckForUpdatesWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()
        WorkManager
            .getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                CHECK_UPDATES_WORK,
                ExistingPeriodicWorkPolicy.KEEP,
                uploadWorkRequest
            )
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val manager: NotificationManager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}