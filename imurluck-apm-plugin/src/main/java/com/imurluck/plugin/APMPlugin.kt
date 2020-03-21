package com.imurluck.plugin

import com.android.build.gradle.AppExtension
import com.imurluck.transform.APMTransform
import com.imurluck.transform.AppColdBootConsumer
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * gradle plugin to transform class file for implementing APM features
 * create by imurluck
 * create at 2020-03-18
 */
class APMPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.findByType<AppExtension>(AppExtension::class.java)?.apply {
            registerTransform(APMTransform().apply {
                registerTransformConsumer(AppColdBootConsumer())
            })
        }
    }
}