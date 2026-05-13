import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories.mavenCentral()

plugins {
    id("org.jetbrains.kotlin.jvm")
}

version = "0.2.0"

tasks.getByName<JavaCompile>("compileJava") {
    targetCompatibility = "17"
}

val compileKotlinTask = tasks.getByName<KotlinCompile>("compileKotlin") {
    compilerOptions.jvmTarget = JvmTarget.fromTarget("17")
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
}

"snapshot".also { variant ->
    val version = "$version-SNAPSHOT"
    tasks.register<Jar>("assemble${variant.replaceFirstChar(Character::toUpperCase)}Jar") {
        dependsOn(compileKotlinTask)
        archiveBaseName = rootProject.name
        archiveVersion = version
        from(compileKotlinTask.destinationDirectory.asFileTree)
    }
}
