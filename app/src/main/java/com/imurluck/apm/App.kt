package com.imurluck.apm

import android.app.Application
import com.imurluck.monitor.fps.FpsMonitor

/**
 * for
 * create by imurluck
 * create at 2020-03-07
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FpsMonitor().install(this)
    }
}