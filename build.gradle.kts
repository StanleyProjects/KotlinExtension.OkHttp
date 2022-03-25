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

task("verifyLicense") {
    doLast {
        val file = File(rootDir, "LICENSE")
        val text = file.existing().readText(Charsets.UTF_8).filled()
        // todo
    }
}

task("verifyReadme") {
    doLast {
        val badges = setOf(
            MarkdownUtil.image(
                text = "version",
                url = BadgeUtil.url(
                    label = "version",
                    message = Version.name,
                    color = "2962ff"
                )
            )
        )
        fun check(file: File): Set<String> {
            if (!file.exists()) return setOf("the file does not exist")
            if (file.isDirectory) return setOf("the file is a directory")
            val text = file.readText(Charsets.UTF_8)
            if (text.isEmpty()) return setOf("the file does not contain text")
            val result = mutableSetOf<String>()
            val lines = text.split(SystemUtil.newLine)
            badges.forEach {
                if (!lines.contains(it)) result.add("the file does not contain \"$it\" line")
            }
            return result
        }
        val issues = check(file = File(rootDir, "README.md"))
        val report = File(buildDir, "reports/analysis/readme/index.html").also {
            it.delete()
            it.parentFile!!.mkdirs()
        }
        if (issues.isEmpty()) {
            val message = "All checks of the file along the \"README.md\" were successful."
            report.writeText(message)
            println(message)
        } else {
            val text = """
<html>
<h3>The following problems were found while checking the <code>README.md</code>:</h3>
${issues.joinToString(prefix = "<ul>", postfix = "</ul>", separator = "\n") { "<li>$it</li>" }}
</html>
            """.trimIndent()
            report.writeText(text)
            error("Problems were found while checking the \"README.md\". See the report ${report.absolutePath}")
        }
    }
}

task("verifyService") {
    doLast {
        val forbiddenFileNames = setOf(".DS_Store")
        rootDir.forEachRecurse {
            if (!it.isDirectory) check(!forbiddenFileNames.contains(it.name)) {
                "File by path ${it.absolutePath} is forbidden!"
            }
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
        "--reporter=html,output=${File(buildDir, "reports/analysis/code/style/html/index.html")}"
    )
}
