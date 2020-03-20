package com.imurluck.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.imurluck.base.IALog
import com.imurluck.base.traversalDirectoryInput
import com.imurluck.base.traversalJarInput
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

/**
 * for
 * create by imurluck
 * create at 2020-03-18
 */
class APMTransform : Transform() {

    private val consumerList = mutableListOf<TransformConsumer>()

    override fun getName(): String =
        TRANSFORM_NAME

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> = TransformManager.CONTENT_CLASS

    override fun isIncremental(): Boolean = true

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> = TransformManager.SCOPE_FULL_PROJECT

    override fun transform(transformInvocation: TransformInvocation?) {
        IALog.i("start to transform app cold boot")
        transformInvocation?.inputs?.forEach { input ->
            input.directoryInputs?.forEach { directoryInput ->
                val outputFile = transformInvocation.outputProvider.getContentLocation(name, inputTypes, scopes, Format.DIRECTORY)
                directoryInput.traversalDirectoryInput(directoryInput.file) { fis, name ->
                    notifyConsumerWithDirectoryFile(fis, name, outputFile)
                }
            }
            val jarOutputFile = transformInvocation.outputProvider.getContentLocation(name, inputTypes, scopes, Format.JAR)
            input.jarInputs?.forEach { jarInput ->
                JarOutputStream(jarOutputFile.outputStream()).use { jos ->
                    jarInput.traversalJarInput { jarFile, jarEntry ->
                        notifyConsumerWithJarFile(jarFile, jarEntry, jos)
                    }
                }
            }
        }
    }

    /**
     * notify consumer to consume local directory file
     * the next consumer will consume the before one's return
     */
    private fun notifyConsumerWithDirectoryFile(fis: InputStream, name: String, outputFile: File) {
        FileOutputStream(outputFile).use { fos ->
            if (!accept(name)) {
                fis.copyTo(fos)
                return
            }
            var consumed = false
            var fileByte: ByteArray? = null
            for (consumer in consumerList) {
                if (consumer.accept(name)) {
                    consumed = true
                    fileByte = if (fileByte != null) {
                        consumer.consume(fileByte)
                    } else {
                        consumer.consume(fis.readBytes())
                    }
                }
            }
            if (consumed && fileByte != null) {
                fileByte.inputStream().copyTo(fos)
            } else {
                fis.copyTo(fos)
            }
        }
    }

    /**
     * notify consumer to consume jar entry file
     * the next consumer will consume the before one's return
     */
    private fun notifyConsumerWithJarFile(jarFile: JarFile, jarEntry: JarEntry, jos: JarOutputStream) {
        jos.putNextEntry(jarEntry)
        if (!accept(jarEntry.name)) {
            jarFile.getInputStream(jarEntry).copyTo(jos)
            return
        }
        var consumed = false
        var fileByte: ByteArray? = null
        for (consumer in consumerList) {
            if (consumer.accept(jarEntry.name)) {
                consumed = true
                fileByte = if (fileByte != null) {
                    consumer.consume(fileByte)
                } else {
                    consumer.consume(jarFile.getInputStream(jarEntry).readBytes())
                }
            }
        }
        if (consumed && fileByte != null) {
            fileByte.inputStream().copyTo(jos)
        } else {
            jarFile.getInputStream(jarEntry).copyTo(jos)
        }
    }

    private fun accept(name: String): Boolean = name.substringAfterLast(".", "") == FILL_SUFFIX_CLASS

    companion object {

        private const val TRANSFORM_NAME = "AppColdBootTransform"

        private const val FILL_SUFFIX_CLASS = "class"

    }
}