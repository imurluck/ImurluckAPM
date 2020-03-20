package com.imurluck.base

import com.android.build.api.transform.QualifiedContent
import java.io.File
import java.io.InputStream
import java.util.jar.JarEntry
import java.util.jar.JarFile

/**
 * traversal classes from local directory
 */
internal fun QualifiedContent.traversalDirectoryInput(
    inputFile: File,
    callback: (InputStream, String) -> Unit
) {
    if (inputFile.isDirectory) {
        inputFile.listFiles()?.forEach { it ->
            traversalDirectoryInput(it, callback)
        }
    } else if (inputFile.isFile) {
        inputFile.inputStream().use { fis ->
            callback(fis, inputFile.name)
        }

    }
}

/**
 * traversal classes from jar file
 * [transform]
 */
internal fun QualifiedContent.traversalJarInput(
    callback: (JarFile, JarEntry) -> Unit
) {
    val jarFile = JarFile(file)
    jarFile.entries().asSequence().forEach { jarEntry ->
        callback(jarFile, jarEntry)
    }
}

