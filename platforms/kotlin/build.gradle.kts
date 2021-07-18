val kotlinVersion: String by project

plugins {
    `java-platform`
    `maven-publish`
    id("com.github.ben-manes.versions") version "0.39.0"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://minecraft.curseforge.com/api/maven/")
    maven("https://erethon.de/repo/")
}

dependencies {
    constraints {
        api("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

        api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
        api("org.jetbrains.kotlinx:kotlinx-serialization-cbor:1.2.2")
        api("com.charleskorn.kaml:kaml:0.34.0")

        api("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.5.1")
        api("org.jetbrains.exposed:exposed-dao:0.32.1")
        api("org.jetbrains.exposed:exposed-jdbc:0.32.1")

        api("de.erethon:headlib:3.0.6")
        api("com.github.okkero:skedule:1.2.6")
        api("org.nield:kotlin-statistics:1.2.1")
        api("org.xerial:sqlite-jdbc:3.36.0.1")
    }
}

//TODO try to fnd a way to reuse code from our conventions plugins
publishing {
    repositories {
        maven("https://repo.mineinabyss.com/releases") {
            credentials {
                username = project.findProperty("mineinabyssMavenUsername") as String?
                password = project.findProperty("mineinabyssMavenPassword") as String?
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["javaPlatform"])
        }
    }
}
