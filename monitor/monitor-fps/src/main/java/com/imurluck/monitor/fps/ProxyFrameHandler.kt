package com.imurluck.monitor.fps

import android.os.Handler
import android.os.Message
import com.imurluck.apm.core.log.IALog

/**
 * for
 * create by imurluck
 * create at 2020-03-08
 */
class ProxyFrameHandler(private val originHandler: Handler) : Handler() {

    override fun handleMessage(msg: Message) {
        IALog.e(TAG, "msg is ${msg.what}")
        originHandler.handleMessage(msg)
    }

    companion object {
        private const val TAG = "ProxyFrameHandler"
    }
}