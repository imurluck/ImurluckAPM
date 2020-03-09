package com.imurluck.apm

import android.app.Application
import com.imurluck.monitor.fps.FpsMonitor
import com.imurluck.monitor.fps.UILooperMonitor

/**
 * for
 * create by imurluck
 * create at 2020-03-07
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        UILooperMonitor.install(this)
        FpsMonitor().install(this)
    }
}