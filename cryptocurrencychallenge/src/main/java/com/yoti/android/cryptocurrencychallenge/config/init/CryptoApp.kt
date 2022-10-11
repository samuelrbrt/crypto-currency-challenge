package com.yoti.android.cryptocurrencychallenge.config.init

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

@HiltAndroidApp
class CryptoApp : Application() {
    val appScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        System.setProperty("kotlinx.coroutines.debug", "on")
        Timber.plant(Timber.DebugTree())
    }
}