package com.mineinabyss

import org.gradle.api.DefaultTask
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.named
import org.gradle.language.jvm.tasks.ProcessResources

class SetupDSL(private val project: Project) {
    private val run = mutableSetOf<Function0<*>>()
    private val alreadyRan = mutableSetOf<Function<*>>()

    private fun runOnce(func: Project.() -> Unit) {
        if (func in alreadyRan) return
        project.func()
        alreadyRan += func
    }

    fun all() {
        run += setOf(
            ::applyJavaDefaults,
            ::processResources,
            ::addGithubRunNumber,
            ::copyJar,
        )
    }

    fun except(vararg functions: Function0<*> = arrayOf()) {
        run -= functions
    }

    internal fun execute() = run.forEach { it() }

    fun applyJavaDefaults() = runOnce {
        extensions.configure<JavaPluginExtension>("java") {
            sourceCompatibility = JavaVersion.VERSION_1_8
            withSourcesJar()
        }
    }

    fun processResources() = runOnce {
        tasks.named<ProcessResources>("processResources") {
            expand(mutableMapOf("plugin_version" to version))
        }
    }

    fun addGithubRunNumber() = runOnce {
        val runNumber = System.getenv("GITHUB_RUN_NUMBER")
        if (runNumber != null) version = "$version.$runNumber"
    }

    fun copyJar() = runOnce {
        val pluginPath = project.findProperty("plugin_path") ?: return@runOnce

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
}

fun Project.sharedSetup(init: (SetupDSL.() -> Unit)? = null) {
    val setup = project.extensions.findByType(SetupDSL::class.java)
        ?: SetupDSL(project).also { project.extensions.add("sharedSetup", it) }

    setup.apply {
        if (init == null) all()
        else init()
        execute()
    }
}
