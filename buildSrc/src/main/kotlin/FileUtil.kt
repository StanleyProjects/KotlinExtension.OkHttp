import java.io.File

fun File.existing(): File {
    check(exists()) { "File by path \"$absolutePath\" does not exist!" }
    return this
}

fun File.forEachRecurse(action: (File) -> Unit) {
    action(this)
    if (isDirectory) {
        listFiles()?.forEach {
            it.forEachRecurse(action)
        }
    }
}
