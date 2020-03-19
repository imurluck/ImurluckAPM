package com.imurluck.base

import org.gradle.api.logging.Logging

/**
 * for
 * create by imurluck
 * create at 2020-03-18
 */
object IALog {

    var showLog = true

    private const val NAME = "IALog"
    private val logging = Logging.getLogger(NAME)

    fun i(msg: String) {
        showLog.ifDo {
            logging.info(msg)
        }
    }

    fun w(msg: String) {
        showLog.ifDo {
            logging.warn(msg)
        }
    }

    fun d(msg: String) {
        showLog.ifDo {
            logging.debug(msg)
        }
    }

    fun e(msg: String) {

        showLog.ifDo {
            logging.error(msg)
        }
    }
}