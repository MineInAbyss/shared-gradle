plugins {
    java
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.7.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.kotest:kotest-runner-junit5:4.6.0")
    testImplementation("io.kotest:kotest-property:4.6.0")
    testImplementation("io.mockk:mockk:1.10.6")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
