package sp.kx.okhttp.util.org.junit

import org.junit.jupiter.api.Assertions.assertTrue

inline fun <reified T : Any> Any.assertType() {
    assertTrue(this is T)
}
