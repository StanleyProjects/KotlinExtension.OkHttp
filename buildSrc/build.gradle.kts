plugins {
    id("org.gradle.kotlin.kotlin-dsl") version "1.4.9"
}

repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation(
        group = "com.github.kepocnhh",
        name = "KotlinExtension.GradleUtil",
        version = "0.0.2-SNAPSHOT"
    )
}
