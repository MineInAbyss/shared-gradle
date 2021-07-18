val serverVersion: String by project
val useNMS: String? by project

plugins {
    java
    id("com.mineinabyss.conventions.copyjar")
}

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.codemc.io/repository/nms/")
}

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:$serverVersion")
    if(useNMS.toBoolean())
        compileOnly("com.destroystokyo.paper:paper:$serverVersion") // NMS
}

tasks.processResources {
    expand(mutableMapOf("plugin_version" to version))
}
