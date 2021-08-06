repositories.mavenCentral()

plugins {
    apply(P.application, P.kotlinJvm)
}

application {
    mainClass.set("sp.service.sample.AppKt")
}

dependencies {
//    implementation(project(":lib"))
}
