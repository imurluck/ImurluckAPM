package com.imurluck.apm.core.extensions

import java.lang.Exception
import java.lang.reflect.Method

/**
 * get field from an object by reflect
 */
inline fun <reified T> Any.getField(name: String): T? {
//    return try {
//        val field = this.javaClass.getDeclaredField(name)
//        if (!field.isAccessible) {
//            field.isAccessible = true
//        }
//        field.get(this) as T
//    } catch (e: Exception) {
//        null
//    }
    return RealReflectHelper.getField(this, name)
}

/**
 * set field
 */
fun Any.setField(arg: Any, name: String) {
    try {
        val field = this.javaClass.getDeclaredField(name)
        field.isAccessible = true
        field.set(this, arg)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * get method from an object by reflect
 */
fun Any.getMethod(name: String, vararg paramTypes: Class<*>): Method? {
    return try {
        val method = javaClass.getDeclaredMethod(name, *paramTypes)
        if (!method.isAccessible) {
            method.isAccessible = true
        }
        method
    } catch (e: Exception) {
        null
    }
}
