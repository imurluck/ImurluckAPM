package com.imurluck.transform

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassReader.EXPAND_FRAMES
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.ClassWriter.COMPUTE_MAXS
import org.objectweb.asm.tree.ClassNode


/**
 * for
 * create by imurluck
 * create at 2020-03-21
 */
abstract class ClassConsumer : TransformConsumer() {

    /**
     * transform class byte array to [ClassNode]
     */
    override fun consume(fileBytes: ByteArray): ByteArray {
        return ClassNode().run {
            ClassReader(fileBytes).accept(this, 0)
            visitClass(this)
        }.run {
            val classWriter = ClassWriter(COMPUTE_MAXS)
            accept(classWriter)
            classWriter.toByteArray()
        }
    }

    /**
     * visit class with [ClassNode]
     */
    abstract fun visitClass(classNode: ClassNode): ClassNode
}