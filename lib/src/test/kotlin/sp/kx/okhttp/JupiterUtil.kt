package sp.kx.okhttp

import org.junit.jupiter.api.Assertions

fun <T : Any> assertNotNull(value: T?): T {
    Assertions.assertNotNull(value)
    if (value == null) error("Impossible!")
    return value
}
