val kotlinVersion: String by project

plugins {
    java
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
    id("io.github.slimjar")
}

repositories {
    mavenCentral()
    maven("https://repo.mineinabyss.com/releases")
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly(platform("com.mineinabyss:kotlinspice:${kotlinVersion}+"))
}
