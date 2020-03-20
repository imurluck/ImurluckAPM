package com.imurluck.transform

/**
 * for
 * create by imurluck
 * create at 2020-03-20
 */
abstract class TransformConsumer {

    abstract fun accept(name: String): Boolean

    abstract fun consume(fileBytes: ByteArray): ByteArray
}