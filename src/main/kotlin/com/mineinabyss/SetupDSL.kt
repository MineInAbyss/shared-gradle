package com.mineinabyss

import org.gradle.api.DefaultTask
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.named
import org.gradle.language.jvm.tasks.ProcessResources
import kotlin.reflect.KFunction0

class SetupDSL(private val project: Project) {
    private val run = mutableSetOf<Function0<Any>>()

    fun all() {
        run += setOf(
            ::applyJavaDefaults,
            ::processResources,
            ::addGithubRunNumber,
            ::copyJar,
        )
    }

    fun applyJavaDefaults() = project.run {
        extensions.configure<JavaPluginExtension>("java") {
            sourceCompatibility = JavaVersion.VERSION_1_8
            withSourcesJar()
        }
    }

    fun processResources() = project.run {
        tasks.named<ProcessResources>("processResources") {
            expand(mutableMapOf("plugin_version" to version))
        }
    }

    fun addGithubRunNumber() = project.run {
        val runNumber = System.getenv("GITHUB_RUN_NUMBER")
        if (runNumber != null) version = "$version.$runNumber"
    }

    fun copyJar() = project.run {
        val pluginPath = project.findProperty("plugin_path") ?: return@run

        tasks.register("copyJar", Copy::class.java) {
            from(tasks.named("shadowJar"))
            into(pluginPath)
            doLast {
                println("Copied to plugin directory $pluginPath")
            }
        }

        tasks.named<DefaultTask>("build") {
            dependsOn("copyJar")
        }
    }

    fun except(vararg functions: KFunction0<Any> = arrayOf()) {
        run -= functions
    }

    internal fun execute() = run.forEach { it() }
}

fun Project.sharedSetup(init: (SetupDSL.() -> Unit)? = null) {
    SetupDSL(project).apply {
        if (init == null) all()
        else init()
        execute()
    }
}
