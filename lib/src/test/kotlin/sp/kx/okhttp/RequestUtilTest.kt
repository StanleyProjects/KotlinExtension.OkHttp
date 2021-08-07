package sp.kx.okhttp

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals

internal class RequestUtilTest {
    @Test
    fun newBuilderTest() {
        val httpUrl = "https://github.com/".toHttpUrl()
        val key = "foo"
        val value = "bar"
        val request = Request.Builder().also {
            it.url(httpUrl)
            it.addHeader(key, value)
        }.build()
        assertEquals(request.headers.size, 1)
        assertEquals(request.headers[key], value)
        val keyNew = "baz"
        assertNotEquals(key, keyNew)
        val valueNew = "qux"
        assertNotEquals(value, valueNew)
        val requestNew = request.newBuilder {
            addHeader(keyNew, valueNew)
        }.build()
        assertEquals(requestNew.url, httpUrl)
        assertEquals(requestNew.headers.size, 2)
        assertEquals(requestNew.headers[key], value)
        assertEquals(requestNew.headers[keyNew], valueNew)
    }

    @Test
    fun cloneTest() {
        val httpUrl = "https://github.com/".toHttpUrl()
        val key = "foo"
        val value = "bar"
        val request = Request.Builder().also {
            it.url(httpUrl)
            it.addHeader(key, value)
        }.build()
        assertEquals(request.headers.size, 1)
        assertEquals(request.headers[key], value)
        val keyNew = "baz"
        assertNotEquals(key, keyNew)
        val valueNew = "qux"
        assertNotEquals(value, valueNew)
        val requestNew = request.clone {
            addHeader(keyNew, valueNew)
        }
        assertEquals(requestNew.url, httpUrl)
        assertEquals(requestNew.headers.size, 2)
        assertEquals(requestNew.headers[key], value)
        assertEquals(requestNew.headers[keyNew], valueNew)
    }
}
