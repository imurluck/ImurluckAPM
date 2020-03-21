package com.imurluck.base

internal const val CLASS_NAME_FRAGMENT_ACTIVITY = "FragmentActivity"

private val androidXMap = mapOf(
        CLASS_NAME_FRAGMENT_ACTIVITY to "androidx/fragment/app/FragmentActivity.class"
    )

private val supportMap = mapOf(
    CLASS_NAME_FRAGMENT_ACTIVITY to ""
)

private fun isUseAndroidX() = true

internal fun getClassName(name: String) = if (isUseAndroidX()) androidXMap[name] else supportMap[name]
