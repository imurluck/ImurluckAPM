package com.imurluck.monitor.fps

import android.app.Application
import android.os.Looper
import android.view.Choreographer
import com.imurluck.apm.core.extensions.*
import com.imurluck.apm.core.log.IALog
import com.imurluck.monitor.fps.utils.sdkVersionNotSupport
import java.lang.reflect.Method

/**
 * for
 * create by imurluck
 * create at 2020-03-07
 */
class FpsMonitor: Monitor {

    private lateinit var lock: Any

    private lateinit var choreographer: Choreographer

    private lateinit var callbackQueues: Array<Any>

    private val addCallbackMethod: Array<Method?> = arrayOfNulls<Method?>(3)

    override fun install(application: Application) {
        if (!reflected(application)) {
            return
        }
        startMonitor()
    }

    private fun reflected(application: Application): Boolean {
        lateInitNotNull<Choreographer>(Choreographer.getInstance()).apply {
            if (isNull) {
                sdkVersionNotSupport()
                return false
            }
            choreographer = reference!!
        }
        lateInitNotNull<Any>(choreographer.getField(FIELD_LOCK)).apply {
            if (isNull) {
                sdkVersionNotSupport()
                return false
            }
            lock = reference!!
        }
        lateInitNotNull<Array<Any>>(choreographer.getField(FIELD_CALLBACK_QUEUE)).apply {
            if (isNull) {
                sdkVersionNotSupport()
                return false
            }
            callbackQueues = reference!!
        }
        callbackQueues.apply {
            lateInitNotNull<Method>(get(FRAME_CALLBACK_INPUT)
                .getMethod(METHOD_ADD_CALLBACK, Long::class.java, Any::class.java, Any::class.java)).apply {
                if (isNull) {
                    sdkVersionNotSupport()
                    return false
                }
                addCallbackMethod[FRAME_CALLBACK_INPUT] = reference!!
            }
            lateInitNotNull<Method>(get(FRAME_CALLBACK_INPUT)
                .getMethod(METHOD_ADD_CALLBACK, Long::class.java, Any::class.java, Any::class.java)).apply {
                if (isNull) {
                    sdkVersionNotSupport()
                    return false
                }
                addCallbackMethod[FRAME_CALLBACK_ANIMATION] = reference!!
            }
            lateInitNotNull<Method>(get(FRAME_CALLBACK_TRAVERSAL)
                .getMethod(METHOD_ADD_CALLBACK, Long::class.java, Any::class.java, Any::class.java)).apply {
                if (isNull) {
                    sdkVersionNotSupport()
                    return false
                }
                addCallbackMethod[FRAME_CALLBACK_TRAVERSAL] = reference!!
            }
        }
        return true
    }

    private fun startMonitor() {
        insertCallback(FRAME_CALLBACK_INPUT, Runnable {
            IALog.e(TAG, "callback input")
        })
        insertCallback(FRAME_CALLBACK_ANIMATION, Runnable {
            IALog.e(TAG, "callback animation")
        })
        insertCallback(FRAME_CALLBACK_TRAVERSAL, Runnable {
            IALog.e(TAG, "callback traversal")
        })
    }

    /**
     * insert callback before head node
     */
    private fun insertCallback(callbackType: Int, action: Runnable) {
        synchronized(lock) {
            //-1 means this callback will insert before head node
            addCallbackMethod[callbackType]?.invoke(callbackQueues[callbackType], -1, action, null)
        }
    }

    companion object {

        private const val TAG = "FPSMonitor"

        private const val FIELD_LOCK = "mLock"

        private const val FIELD_CALLBACK_QUEUE = "mCallbackQueues"

        private const val METHOD_ADD_CALLBACK = "addCallbackLocked"

        /**
         * vertical V_SYNC pulse received, callback will invoke
         * type: input
         */
        private const val FRAME_CALLBACK_INPUT = 0

        /**
         * vertical V_SYNC pulse received, callback will invoke
         * type: animation
         * runs after input type
         */
        private const val FRAME_CALLBACK_ANIMATION = 1

        /**
         * vertical V_SYNC pulse received, callback will invoke
         * type: traversal
         * runs after animation type
         */
        private const val FRAME_CALLBACK_TRAVERSAL = 2

        private const val FRAME_CALLBACK_COMMIT = 3
    }
}