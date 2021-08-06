import sp.kx.gradle.entity.dependency
import sp.kx.gradle.entity.plugin

object Version {
    const val kotlin = "1.4.20"
    const val name = "0.0.1"
}

object Group {
    const val jetbrains = "org.jetbrains"
    const val kotlin = "$jetbrains.kotlin"
}

object D {
    val kotlinGradlePlugin = dependency(
        group = Group.kotlin,
        name = "kotlin-gradle-plugin",
        version = Version.kotlin
    )
    val kotlinStdlib = dependency(
        group = Group.kotlin,
        name = "kotlin-stdlib",
        version = Version.kotlin
    )
}

object P {
    val application = plugin(
        name = "org.gradle.application"
    )
    val kotlinJvm = plugin(
        name = Group.kotlin + ".jvm",
        version = Version.kotlin
    )
}
