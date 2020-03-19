package com.imurluck.base

internal fun Boolean.ifDo(block: () -> Unit) {
    if (this) {
        block()
    }
}