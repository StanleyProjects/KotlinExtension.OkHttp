fun String.filled(): String {
    check(isNotEmpty()) { "Value is empty!" }
    return this
}
