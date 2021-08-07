repositories.mavenCentral()

plugins {
    apply(P.kotlinJvm)
}

project.version = Version.name

dependencies {
    implementation(D.okhttp)
    testImplementation(D.jupiterApi)
    testRuntimeOnly(D.jupiterEngine)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val groupId = "com.github.kepocnhh"
val artifactId = rootProject.name

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = Version.jvmTarget
    freeCompilerArgs = freeCompilerArgs + setOf("-module-name", "$groupId:$artifactId")
}

"Snapshot".also { type ->
    val versionName = project.version.toString() + "-SNAPSHOT"
    task<Jar>("assemble$type") {
        dependsOn(compileKotlin)
        archiveBaseName.set(artifactId)
        archiveVersion.set(versionName)
        from(compileKotlin.destinationDir)
    }
    task("assemble${type}Pom") {
        doLast {
            val parent = File(buildDir, "libs")
            if (!parent.exists()) parent.mkdirs()
            val file = File(parent, "$artifactId-$versionName.pom")
            if (file.exists()) file.delete()
            file.createNewFile()
            check(file.exists()) { "File by path: ${file.absolutePath} must be exists!" }
            val text = MavenUtil.pom(
                modelVersion = "4.0.0",
                groupId = groupId,
                artifactId = artifactId,
                version = versionName,
                packaging = "jar"
            )
            file.writeText(text)
        }
    }
}

task("getVersionName") {
    doLast {
        println(Version.name)
    }
}
