package com.imurluck.base

/**
 * for
 * create by imurluck
 * create at 2020-03-18
 */
object IALog {

    var showLog = true

    fun log(msg: String) {
        if (showLog) {
            println(msg)
        }
    }
}