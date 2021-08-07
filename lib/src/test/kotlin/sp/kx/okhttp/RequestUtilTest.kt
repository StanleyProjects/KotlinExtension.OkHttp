package sp.kx.okhttp

import java.net.URL
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

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

    private fun assertSame(expected: URL, actual: URL) {
        assertEquals(expected.host, actual.host)
        assertEquals(expected.protocol, actual.protocol)
    }

    private fun assertQueries(queries: Map<String, String>, request: Request) {
        assertEquals(request.url.queryParameterNames.size, queries.keys.size)
        queries.forEach { (key, value) ->
            val values = request.url.queryParameterValues(key)
            assertEquals(values.size, 1)
            assertEquals(values.first(), value)
        }
    }

    private fun assertHeaders(headers: Map<String, String>, request: Request) {
        assertEquals(request.headers.size, headers.size)
        headers.forEach { (key, value) ->
            val values = request.headers(key)
            assertEquals(values.size, 1)
            assertEquals(values.first(), value)
        }
    }

    @Test
    fun requestUrlQueriesHeadersTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val headers = mapOf("baz" to "qux")
        val request = request(
            url = url,
            queries = queries,
            headers = headers
        )
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertQueries(queries = queries, request = request)
        assertHeaders(headers = headers, request = request)
    }

    @Test
    fun requestBuilderUrlQueriesHeadersBodyTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val headers = mapOf("baz" to "qux")
        val method = Method.POST
        val body = "body".toRequestBody()
        val request = requestBuilder(
            url = url,
            queries = queries,
            headers = headers,
            method = method,
            body = body
        ).build()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertQueries(queries = queries, request = request)
        assertHeaders(headers = headers, request = request)
        assertEquals(request.method, method.name)
        assertTrue(request.body === body) {
            "It is not the same object!"
        }
    }

    @Test
    fun requestUrlQueriesHeadersBodyTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val headers = mapOf("baz" to "qux")
        val method = Method.POST
        val body = "body".toRequestBody()
        val request = request(
            url = url,
            queries = queries,
            headers = headers,
            method = method,
            body = body
        )
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertQueries(queries = queries, request = request)
        assertHeaders(headers = headers, request = request)
        assertEquals(request.method, method.name)
        assertTrue(request.body === body) {
            "It is not the same object!"
        }
    }
}
