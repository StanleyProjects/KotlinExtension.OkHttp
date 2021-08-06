buildscript {
    repositories.mavenCentral()

    dependency(classpath = D.kotlinGradlePlugin)
}

task<Delete>("clean") {
    delete = setOf(rootProject.buildDir)
}
