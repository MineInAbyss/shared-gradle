[![Publish Packages](https://github.com/MineInAbyss/shared-gradle/actions/workflows/publish-packages.yml/badge.svg)](https://github.com/MineInAbyss/shared-gradle/actions/workflows/publish-packages.yml)
[![Maven](https://badgen.net/maven/v/metadata-url/repo.mineinabyss.com/releases/com/mineinabyss/shared-gradle/maven-metadata.xml)](https://repo.mineinabyss.com/releases/com/mineinabyss/shared-gradle)
[![Discord](https://badgen.net/discord/members/QXPCk2y)](https://discord.gg/QXPCk2y)
[![Contribute](https://shields.io/badge/Contribute-e57be5?logo=github%20sponsors&style=flat&logoColor=white)](https://github.com/MineInAbyss/MineInAbyss/wiki/Setup-and-Contribution-Guide)

# shared-gradle

Code that helps us share common shortcuts for our buildscripts. Includes a gradle plugin written in Kotlin, plus some groovy scripts that can be applied directly via url.

## Features
- `miaSharedSetup()` DSL for running commonly executed tasks:
  - Sets java sources version to 1.8, asks to provide sources jar
  - processResources task to replace plugin version token in the plugin config
  - Adds .GITHUB_RUN_NUMBER to the project `version`
  - copyJar into a plugin_path defined in your global gradle.properties
- Some shortcuts for our own repos like the `mineInAbyss()` repository or special block inside `publishing { }`

## Using the plugin

This plugin provides many extension functions as well as a dsl for common setup actions we don't want to repeat.

We host this on our own maven repo since there is little use for this plugin outside sharing our own build scripts, and this avoids potential wait times on the gradle plugin portal.

#### `build.gradle.kts`
 ```kotlin
 plugins {
     id("com.mineinabyss.shared-gradle") version "0.0.1"
 }
 
 miaSharedSetup() // DSL for running tasks we commonly want to reuse like copyJar
 ```
 
#### `settings.gradle.kts`
```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.mineinabyss.com/releases") // Add our repository to be able to access the plugin
    }
}
```

## Groovy files

Some files are meant to be applied inside plugins. We'll likely be phasing these out in favor of type-safe Kotlin DSLs.

```groovy
apply from: 'https://raw.githubusercontent.com/MineInAbyss/shared-gradle/master/common.gradle'
```
