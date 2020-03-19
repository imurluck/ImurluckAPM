package com.imurluck.plugin

import com.android.build.gradle.AppExtension
import com.imurluck.transform.AppColdBootTransform
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
            registerTransform(AppColdBootTransform())
        }
    }
}