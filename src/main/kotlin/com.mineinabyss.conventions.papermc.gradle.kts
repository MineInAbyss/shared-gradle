import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val serverVersion: String by project
val useNMS: String? by project

plugins {
    java
    id("com.mineinabyss.conventions.copyjar")
}

java {
    targetCompatibility = JavaVersion.VERSION_16
}

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.codemc.io/repository/nms/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:$serverVersion")
    if(useNMS.toBoolean())
        compileOnly("io.papermc.paper:paper:$serverVersion") // NMS
}

tasks {
    processResources {
        expand(mutableMapOf("plugin_version" to version))
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "16"
    }
}
