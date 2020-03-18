package com.imurluck.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.imurluck.base.IALog

/**
 * for
 * create by imurluck
 * create at 2020-03-18
 */
class AppColdBootTransform : Transform() {


    override fun getName(): String =
        TRANSFORM_NAME

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> = TransformManager.CONTENT_CLASS

    override fun isIncremental(): Boolean = true

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> = TransformManager.SCOPE_FULL_PROJECT

    override fun transform(transformInvocation: TransformInvocation?) {
        IALog.log("start to transform app cold boot")
    }

    companion object {
        private const val TRANSFORM_NAME = "AppColdBootTransform"
    }
}