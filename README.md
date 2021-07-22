[![Publish Packages](https://github.com/MineInAbyss/shared-gradle/actions/workflows/publish-packages.yml/badge.svg)](https://github.com/MineInAbyss/shared-gradle/actions/workflows/publish-packages.yml)
[![Maven](https://badgen.net/maven/v/metadata-url/repo.mineinabyss.com/releases/com/mineinabyss/shared-gradle/maven-metadata.xml)](https://repo.mineinabyss.com/releases/com/mineinabyss/shared-gradle)
[![Discord](https://badgen.net/discord/members/QXPCk2y)](https://discord.gg/QXPCk2y)
[![Contribute](https://shields.io/badge/Contribute-e57be5?logo=github%20sponsors&style=flat&logoColor=white)](https://github.com/MineInAbyss/MineInAbyss/wiki/Setup-and-Contribution-Guide)

# shared-gradle

Code that helps us share common shortcuts for our buildscripts. The project is a plugin itself which provides some
shared functions we may want to reuse, as well as several other conventions plugins that apply common build logic.

## Usage

Add the mineinabyss repo to `settings.gradle.kts`
```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.mineinabyss.com/releases") // Add our repository to be able to access the plugin
    }
}
```

Apply a plugin in your `plugins { }` block. All of them start with `com.mineinabyss.conventions`

```kotlin
plugins {
  id("com.mineinabyss.conventions.SOMETHING")
}
```

Some conventions have extra config options that may be specified in your gradle.properties, they are explained further down.

### Use the same version for all conventions

`settings.gradle.kts`:
```kotlin
pluginManagement {
  val miaConventionsVersion: String by settings

  resolutionStrategy {
    eachPlugin {
      if (requested.id.id.startsWith("com.mineinabyss.conventions"))
        useVersion(miaConventionsVersion)
    }
  }
}
```

Specify the version in `gradle.properties`

## Conventions

### com.mineinabyss.conventions.copyjar

Copies a generated `shadowJar` artifact to a specified path.

- `plugin_path: String` The path to copy the jar to. (should be set in global gradle.properties.)
- `copyJar: Boolean?` if false, will not run.

### com.mineinabyss.conventions.kotlin

Adds Kotlin, shadowjar and slimjar plugins. Applies our KotlinSpice platform of dependencies.

### com.mineinabyss.conventions.papermc

Adds paper dependencies, process resources task which replaces `${plugin_version}` in plugin.yml with the project's `version`. Targets JVM 16.

- `serverVersion: String` the full Minecraft server version name.
- `useNMS: Boolean?` if true, will depend on NMS.

### com.mineinabyss.conventions.publication

Publishes to our maven repo with sources. Adds GitHub run number to the end of version.

- `runNumberDelimiter: String? = "."` the characters to put in between the version and run number.
- `addRunNumber: String?` if false, will not add run number.
- `publishComponentName: String? = "java"` the name of the component to be published.
- `mineinabyssMavenUsername: String`
- `mineinabyssMavenPassword: String`

### com.mineinabyss.conventions.testing

Uses jUnit platform for testing, adds kotest and mockk dependencies.
