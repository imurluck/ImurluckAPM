package com.imurluck.transform

import com.imurluck.base.CLASS_NAME_FRAGMENT_ACTIVITY
import com.imurluck.base.getClassName
import org.objectweb.asm.tree.ClassNode

class AppColdBootConsumer : ClassConsumer() {

    private val acceptClasses = listOf(
        getClassName(CLASS_NAME_FRAGMENT_ACTIVITY)
    )

    override fun accept(name: String): Boolean = acceptClasses.contains(name)

    override fun visitClass(classNode: ClassNode): ClassNode {
        println(classNode.name)
        return classNode
    }
}