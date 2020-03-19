package com.imurluck.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.imurluck.base.IALog
import com.imurluck.base.traversalInput
import java.io.File

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
        IALog.i("start to transform app cold boot")
        transformInvocation?.inputs?.forEach { input ->
            input.directoryInputs?.forEach { directoryInput ->
                println("directory path is " + directoryInput.file.absolutePath)
                directoryInput.traversalInput(transformInvocation)
//                if (directoryInput.file.isDirectory) {
//                    directoryInput.file.
//                }
//                val destFile = transformInvocation.outputProvider.getContentLocation(name, inputTypes, scopes, Format.DIRECTORY)
//                file.copyTo(destFile)
            }
//            input.jarInputs?.forEach { jarInput ->
//                println("jar file path is " + jarInput.file.absolutePath)
//            }
        }
    }

    companion object {
        private const val TRANSFORM_NAME = "AppColdBootTransform"
    }
}