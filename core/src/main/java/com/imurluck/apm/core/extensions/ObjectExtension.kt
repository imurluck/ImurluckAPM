package com.imurluck.apm.core.extensions

/**
 * Safely assign a variable when you don't want to use "?" And forced to use lateinit
 */
inline fun <reified T> lateInitNotNull(source: Any?): LateInitReference<T> {
    if (source == null || source !is T) {
        return LateInitReference(null, true)
    }
    return LateInitReference(source as T, false)
}


class LateInitReference<T>(reference: T?, isNull: Boolean) {

    var reference: T? = reference

    var isNull: Boolean = isNull
}