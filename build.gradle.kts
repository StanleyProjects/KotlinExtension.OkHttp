buildscript {
    repositories.mavenCentral()

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}")
    }
}

task<Delete>("clean") {
    delete = setOf(rootProject.buildDir, "buildSrc/build")
}

task("saveCommonInfo") {
    doLast {
        val result = mapOf(
            "version" to mapOf(
                "name" to Version.name
            ),
            "repository" to mapOf(
                "owner" to Repository.owner,
                "name" to Repository.name
            )
        ).toList().joinToString(prefix = "{", postfix = "}") { (key, value) ->
            "\"$key\": " + value.toList().joinToString(prefix = "{", postfix = "}") { (k, v) ->
                "\"$k\": \"$v\""
            }
        }
        File(buildDir, "common.json").also {
            it.parentFile.mkdirs()
            it.delete()
            it.writeText(result)
        }
    }
}

repositories.mavenCentral() // com.pinterest.ktlint

val kotlinLint: Configuration by configurations.creating

dependencies {
    kotlinLint("com.pinterest:ktlint:${Version.kotlinLint}") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

task<JavaExec>("verifyCodeStyle") {
    classpath = kotlinLint
    mainClass.set("com.pinterest.ktlint.Main")
    args(
        "build.gradle.kts",
        "settings.gradle.kts",
        "buildSrc/src/main/kotlin/**/*.kt",
        "buildSrc/build.gradle.kts",
        "lib/src/main/kotlin/**/*.kt",
        "lib/src/test/kotlin/**/*.kt",
        "lib/build.gradle.kts",
        "--reporter=html,output=${File(buildDir, "reports/analysis/style/html/index.html")}"
    )
}
