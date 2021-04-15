plugins {
    `kotlin-dsl`
    `maven-publish`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

gradlePlugin {
    plugins {
        register("shared-gradle") {
            displayName = "Shared gradle"
            id = "com.mineinabyss.shared-gradle"
            implementationClass = "com.mineinabyss.SharedGradlePlugin"
            description = "Shared gradle scripts for our org"
        }
    }
}

publishing {
    repositories {
        maven("https://repo.mineinabyss.com/releases") {
            credentials {
                username = project.findProperty("mineinabyssMavenUsername") as String
                password = project.findProperty("mineinabyssMavenPassword") as String
            }
        }
    }
    publications {
        register("maven", MavenPublication::class) {
            artifactId = "shared-gradle"
        }
    }
}
