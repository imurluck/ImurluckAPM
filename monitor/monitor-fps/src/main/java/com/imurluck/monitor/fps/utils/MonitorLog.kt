package com.imurluck.monitor.fps.utils

import com.imurluck.apm.core.log.IALog

fun Any.sdkVersionNotSupport() {
    IALog.e(javaClass.name, "SDK version not support!")
}