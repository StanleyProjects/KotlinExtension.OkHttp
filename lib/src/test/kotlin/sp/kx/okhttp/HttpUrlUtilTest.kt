package sp.kx.okhttp

import okhttp3.HttpUrl.Companion.toHttpUrl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals

internal class HttpUrlUtilTest {
    @Test
    fun newBuilderTest() {
        val httpUrl = "https://github.com/".toHttpUrl()
        val key = "foo"
        val value = "bar"
        val oldHttpUrl = httpUrl.newBuilder().also {
            it.addQueryParameter(key, value)
        }.build()
        assertEquals(oldHttpUrl.queryParameterNames.size, 1)
        assertEquals(oldHttpUrl.queryParameterNames.first(), key)
        oldHttpUrl.queryParameterValues(key).also {
            assertEquals(it.size, 1)
            assertEquals(it.first(), value)
        }
        val keyNew = "baz"
        assertNotEquals(key, keyNew)
        val valueNew = "qux"
        assertNotEquals(value, valueNew)
        val newHttpUrl = oldHttpUrl.newBuilder {
            addQueryParameter(keyNew, valueNew)
        }.build()
        assertEquals(newHttpUrl.queryParameterNames.size, 2)
        assertEquals(newHttpUrl.queryParameterNames, setOf(key, keyNew))
        newHttpUrl.queryParameterValues(key).also {
            assertEquals(it.size, 1)
            assertEquals(it.first(), value)
        }
        newHttpUrl.queryParameterValues(keyNew).also {
            assertEquals(it.size, 1)
            assertEquals(it.first(), valueNew)
        }
    }

    @Test
    fun cloneTest() {
        val httpUrl = "https://github.com/".toHttpUrl()
        val key = "foo"
        val value = "bar"
        val oldHttpUrl = httpUrl.newBuilder().also {
            it.addQueryParameter(key, value)
        }.build()
        assertEquals(oldHttpUrl.queryParameterNames.size, 1)
        assertEquals(oldHttpUrl.queryParameterNames.first(), key)
        oldHttpUrl.queryParameterValues(key).also {
            assertEquals(it.size, 1)
            assertEquals(it.first(), value)
        }
        val keyNew = "baz"
        assertNotEquals(key, keyNew)
        val valueNew = "qux"
        assertNotEquals(value, valueNew)
        val newHttpUrl = oldHttpUrl.clone {
            addQueryParameter(keyNew, valueNew)
        }
        assertEquals(newHttpUrl.queryParameterNames.size, 2)
        assertEquals(newHttpUrl.queryParameterNames, setOf(key, keyNew))
        newHttpUrl.queryParameterValues(key).also {
            assertEquals(it.size, 1)
            assertEquals(it.first(), value)
        }
        newHttpUrl.queryParameterValues(keyNew).also {
            assertEquals(it.size, 1)
            assertEquals(it.first(), valueNew)
        }
    }
}
