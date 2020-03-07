package com.imurluck.apm.core.log

import android.util.Log

/**
 * ImurluckApm log helper class
 * create by imurluck
 * create at 2020-03-07
 */
object IALog {

    var IS_DEBUG = true

    private fun IALog.isDebug(block: () -> Unit) {
        if (IS_DEBUG) {
            block()
        }
    }

    fun i(tag: String?, msg: String) {
        isDebug {
            Log.i(tag, msg)
        }
    }

    fun d(tag: String?, msg: String) {
        isDebug {
            Log.d(tag, msg)
        }
    }

    fun w(tag: String?, msg: String) {
        isDebug {
            Log.w(tag, msg)
        }
    }

    fun e(tag: String?, msg: String) {
        isDebug {
            Log.e(tag, msg)
        }
    }

}