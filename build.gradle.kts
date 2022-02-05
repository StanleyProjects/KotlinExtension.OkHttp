buildscript {
    repositories.mavenCentral()

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}")
    }
}

task<Delete>("clean") {
    delete = setOf(rootProject.buildDir, "buildSrc/build")
}

task("getMavenGroupId") {
    doLast {
        println(Maven.groupId)
    }
}

task("getMavenArtifactId") {
    doLast {
        println(Maven.artifactId)
    }
}

task("getVersionName") {
    doLast {
        println(Version.name)
    }
}

