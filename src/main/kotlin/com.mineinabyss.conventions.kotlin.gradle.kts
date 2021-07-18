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
    slim(kotlin("stdlib-jdk8"))
    slim(platform("com.mineinabyss:kotlinspice:${kotlinVersion}+"))
//    slim(platform("com.mineinabyss.platforms:kotlin:${kotlinVersion}+"))
}
