repositories.mavenCentral()

plugins {
    id("io.gitlab.arturbosch.detekt") version Version.detekt
    id("org.jetbrains.kotlin.jvm")
    id("org.gradle.jacoco")
}

project.version = Version.name

dependencies {
    implementation("com.squareup.okhttp3:okhttp:${Version.okhttp}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${Version.jupiter}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Version.jupiter}")
}

tasks.getByName<JavaCompile>("compileJava") {
    targetCompatibility = Version.jvmTarget
}

val compileKotlinTask = tasks.getByName<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>("compileKotlin") {
    kotlinOptions {
        jvmTarget = Version.jvmTarget
        freeCompilerArgs = freeCompilerArgs + setOf("-module-name", Maven.groupId + ":" + Maven.artifactId)
    }
}

tasks.getByName<JavaCompile>("compileTestJava") {
    targetCompatibility = Version.jvmTarget
}

tasks.getByName<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>("compileTestKotlin") {
    kotlinOptions.jvmTarget = Version.jvmTarget
}

val testTask = tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

jacoco {
    toolVersion = Version.jacoco
}

val testCoverageTask = tasks.getByName<JacocoReport>("jacocoTestReport") {
    dependsOn(testTask)
    reports {
        csv.required.set(false)
        html.required.set(true)
        xml.required.set(false)
    }
}

tasks.getByName<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    dependsOn(testCoverageTask)
    violationRules {
        rule {
            limit {
                minimum = BigDecimal(0.9)
            }
        }
    }
}

task<io.gitlab.arturbosch.detekt.Detekt>("verifyCodeQuality") {
    val detektTask = tasks.getByName<io.gitlab.arturbosch.detekt.Detekt>("detektMain")
    jvmTarget = Version.jvmTarget
    setSource(files("src/main/kotlin", "src/test/kotlin"))
    config.setFrom(File(rootDir, "buildSrc/src/main/resources/detekt/config/potential-bugs.yml"))
    // todo comments
    // todo complexity
    // todo coroutines
    // todo empty-blocks
    // todo exceptions
    // todo formatting
    // todo naming
    // todo performance
    // todo style
    reports {
        xml.required.set(false)
        sarif.required.set(false)
        txt.required.set(false)
        html {
            required.set(true)
            outputLocation.set(File(buildDir, "reports/code/quality/html/index.html"))
        }
    }
    classpath.setFrom(detektTask.classpath)
}

"Snapshot".also { variant ->
    val versionName = Version.name + "-" + variant.toUpperCase()
    task<Jar>("assemble${variant}Jar") {
        dependsOn(compileKotlinTask)
        archiveBaseName.set(Maven.artifactId)
        archiveVersion.set(versionName)
        from(compileKotlinTask.destinationDirectory.asFileTree)
    }
    task<Jar>("assemble${variant}Source") {
        archiveBaseName.set(Maven.artifactId)
        archiveVersion.set(versionName)
        archiveClassifier.set("sources")
        from("src/main")
    }
    task("assemble${variant}Pom") {
        doLast {
            val parent = File(buildDir, "libs")
            if (!parent.exists()) parent.mkdirs()
            val file = File(parent, "${Maven.artifactId}-$versionName.pom")
            if (file.exists()) file.delete()
            val text = MavenUtil.pom(
                modelVersion = "4.0.0",
                groupId = Maven.groupId,
                artifactId = Maven.artifactId,
                version = versionName,
                packaging = "jar"
            )
            file.writeText(text)
        }
    }
}
