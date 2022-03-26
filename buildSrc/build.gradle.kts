repositories.mavenCentral()

plugins {
    id("org.gradle.kotlin.kotlin-dsl") version "2.1.7"
}

tasks.getByName<JavaCompile>("compileJava") {
    targetCompatibility = "1.8"
}

tasks.getByName<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>("compileKotlin") {
    kotlinOptions.jvmTarget = "1.8"
}
