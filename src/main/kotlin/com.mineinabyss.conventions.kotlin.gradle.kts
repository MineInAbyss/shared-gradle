plugins {
    java
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
    id("io.github.slimjar")
}

//TODO try to figure out a clean way to read the version automatically.
val conventionsKotlinVersion = "1.5.30"
val kotlinVersion: String? by project
val idofrontVersion: String? by project

if (kotlinVersion != null && conventionsKotlinVersion != kotlinVersion)
    logger.error(
        """
        kotlinVersion property ($kotlinVersion) is not the same as the one
        applied by the Mine in Abyss conventions plugin $conventionsKotlinVersion.
        
        Will be using $conventionsKotlinVersion for Kotlin plugin and stdlib version.
        Try to remove kotlinVersion from gradle.properties or ensure you are on the same version.
        """.trimIndent()
    )

// Let others read kotlinVersion
if(kotlinVersion == null)
    project.ext["kotlinVersion"] = conventionsKotlinVersion

//TODO get kotlin version from plugin and send warning if it doesnt match specified kotlinVersion
repositories {
    mavenCentral()
    maven("https://repo.mineinabyss.com/releases")
    maven("https://repo.vshnv.tech/releases/") // Slimjar
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
    implementation("io.github.slimjar:slimjar:1.2.5")
    // Default to min version that includes slimjar dependency
    implementation("com.mineinabyss:idofront-slimjar:${idofrontVersion ?: "1.17.1-0.6.24"}")
    slim(platform("com.mineinabyss:kotlinspice:${conventionsKotlinVersion}+"))
}
