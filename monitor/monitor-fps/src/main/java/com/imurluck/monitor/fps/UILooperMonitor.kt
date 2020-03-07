package com.imurluck.monitor.fps

import android.app.Application
import android.os.Looper
import android.util.Printer
import com.imurluck.apm.core.extensions.getField

/**
 * for hook main looper's logging
 * notify observers when a message be handled
 * create by imurluck
 * create at 2020-03-07
 */
object UILooperMonitor : Monitor {

    private const val FIELD_LOGGING = "mLogging"

    private const val MSG_HANDLE_BEFORE = 1
    private const val MSG_HANDLE_FINISH = 2

    private var originLogging: Printer? = null

    private lateinit var proxyLogging: Printer

    private val msgListenerList = mutableListOf<LooperMsgListener>()

    override fun install(application: Application) {
        if (!hookLogging()) {
            return
        }
    }

    private fun hookLogging(): Boolean {
        originLogging = Looper.getMainLooper().getField(FIELD_LOGGING)
        proxyLogging = Printer {
            originLogging?.println(it)
            if (it.isEmpty() || (it[0] != '<' && it[0] != '>')) {
                return@Printer
            }
            dispatchMsgHandle(if (it[0] == '>') MSG_HANDLE_BEFORE else MSG_HANDLE_FINISH)
        }
        Looper.getMainLooper().setMessageLogging(proxyLogging)
        return true
    }

    internal fun addMsgListener(listener: LooperMsgListener) {
        msgListenerList.add(listener)
    }

    /**
     * dispatch when before or after a message be handled in main thread
     */
    private fun dispatchMsgHandle(msgHandleType: Int) {
        for (listener in msgListenerList) {
            when (msgHandleType) {
                MSG_HANDLE_BEFORE -> listener.onBeforeHandle()
                MSG_HANDLE_FINISH -> listener.onFinishHandle()
            }
        }
    }

    internal interface LooperMsgListener {

        fun onBeforeHandle()

        fun onFinishHandle()
    }

}