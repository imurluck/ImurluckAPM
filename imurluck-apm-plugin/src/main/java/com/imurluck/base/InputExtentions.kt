package com.imurluck.base

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.TransformInvocation
import java.io.File

internal fun QualifiedContent.traversalInput(transformInvocation: TransformInvocation) {
    when (this) {
        is DirectoryInput -> traversalDirectoryInput(file)
        is JarInput -> traversalJarInput(file)
    }
}

private fun traversalDirectoryInput(file: File) {
    if (file.isDirectory) {
        println("directory is ${file.absolutePath}")
        file.listFiles()?.forEach { it ->
            traversalDirectoryInput(it)
        }
    } else if (file.isFile) {
        println("directory file path is ${file.absolutePath}")
    }
}

private fun traversalJarInput(file: File) {

}

