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
    implementation("io.github.slimjar:slimjar:1.2.4")
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly(platform("com.mineinabyss:kotlinspice:${kotlinVersion}+"))
}
