package com.mineinabyss

import org.gradle.api.DefaultTask
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.language.jvm.tasks.ProcessResources

class SetupDSL(private val project: Project) {
    var applyJavaDefaults = true
    var processResources = true
    var addGithubRunNumber = true
    var copyJar = true

    private val pluginVersion: String by project

    fun applyToProject() = project.run {
        if (applyJavaDefaults)
            extensions.configure<JavaPluginExtension>("java") {
                sourceCompatibility = JavaVersion.VERSION_1_8
                withSourcesJar()
            }
        if (processResources)
            tasks.named<ProcessResources>("processResources") {
                expand(mapOf("plugin_version" to pluginVersion))
            }

        if (addGithubRunNumber) {
            val runNumber = System.getenv("GITHUB_RUN_NUMBER")
            if (runNumber != null) version = "$version.$runNumber"
        }

        val plugin_path: String? by project
        println(plugin_path)
        if (copyJar && plugin_path != null) {
            tasks.register("copyJar", Copy::class.java) {
                from(tasks.named("shadowJar"))
                into(plugin_path!!)
                doLast {
                    println("Copied to plugin directory $plugin_path")
                }
            }

            tasks.named<DefaultTask>("build") {
                dependsOn("copyJar")
            }
        }
    }
}

fun Project.miaSharedSetup(init: (SetupDSL.() -> Unit)? = null) {
    SetupDSL(project).apply {
        if (init != null) init()
    }.applyToProject()
}
