plugins {
    java
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
    id("io.github.slimjar")
}

//TODO try to figure out a clean way to read the version automatically.
val conventionsKotlinVersion = "1.5.21"
val kotlinVersion: String? by project

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
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
    implementation("io.github.slimjar:slimjar:1.2.5")
    slim(platform("com.mineinabyss:kotlinspice:${conventionsKotlinVersion}+"))
}
