repositories.mavenCentral()

plugins {
    apply(P.kotlinJvm)
}

project.version = Version.name

dependencies {
    implementation(D.okhttp)
}
