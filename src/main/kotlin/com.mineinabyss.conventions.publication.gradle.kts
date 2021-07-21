plugins {
    java
    `maven-publish`
    id("org.jetbrains.dokka")
}

val runNumber: String? = System.getenv("GITHUB_RUN_NUMBER")
if (runNumber != null) version = "$version.$runNumber"

java {
    withSourcesJar()
}

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
        register("maven", MavenPublication::class) {
            from(components["java"])
        }
    }
}
