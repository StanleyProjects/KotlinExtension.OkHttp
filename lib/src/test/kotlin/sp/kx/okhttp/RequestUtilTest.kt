package sp.kx.okhttp

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.net.URL

internal fun assertSame(expected: URL, actual: URL) {
    assertEquals(expected.host, actual.host)
    assertEquals(expected.protocol, actual.protocol)
    assertEquals(expected.path, actual.path)
}

internal fun assertQueries(queries: Map<String, String>, request: Request) {
    assertEquals(request.url.queryParameterNames.size, queries.keys.size)
    queries.forEach { (key, value) ->
        val values = request.url.queryParameterValues(key)
        assertEquals(values.size, 1)
        assertEquals(values.first(), value)
    }
}

internal fun assertHeaders(headers: Map<String, String>, request: Request) {
    assertEquals(request.headers.size, headers.size)
    headers.forEach { (key, value) ->
        val values = request.headers(key)
        assertEquals(values.size, 1)
        assertEquals(values.first(), value)
    }
}

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
    fun requestTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val headers = mapOf("baz" to "qux")
        val method = Method.POST
        val body = "body".toRequestBody()
        val request = request {
            url(httpUrl(url = url, queries = queries))
            headers.forEach { (key, value) ->
                addHeader(key, value)
            }
            when (method) {
                Method.POST -> post(body)
            }
        }
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

    @Test
    fun requestUrlQueriesHeadersMethodTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val headers = mapOf("baz" to "qux")
        val method = Method.POST
        val request = request(
            url = url,
            queries = queries,
            headers = headers,
            method = method
        )
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertQueries(queries = queries, request = request)
        assertHeaders(headers = headers, request = request)
        assertEquals(request.method, method.name)
        val body = assertNotNull(request.body)
        assertEquals(body.contentLength(), 0L)
        assertNull(body.contentType())
    }

    @Test
    fun requestUrlBodyTest() {
        val url = "https://github.com/"
        val method = Method.POST
        val body = "body".toRequestBody()
        val request = request(
            url = url,
            method = method,
            body = body
        )
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertHeaders(headers = emptyMap(), request = request)
        assertEquals(request.method, method.name)
        assertTrue(request.body === body) {
            "It is not the same object!"
        }
    }

    @Test
    fun requestUrlHeadersBodyTest() {
        val url = "https://github.com/"
        val headers = mapOf("baz" to "qux")
        val method = Method.POST
        val body = "body".toRequestBody()
        val request = request(
            url = url,
            headers = headers,
            method = method,
            body = body
        )
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertHeaders(headers = headers, request = request)
        assertEquals(request.method, method.name)
        assertTrue(request.body === body) {
            "It is not the same object!"
        }
    }

    @Test
    fun requestUrlHeaderBodyTest() {
        val url = "https://github.com/"
        val header = "baz" to "qux"
        val method = Method.POST
        val body = "body".toRequestBody()
        val request = request(
            url = url,
            header = header,
            method = method,
            body = body
        )
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertHeaders(headers = mapOf(header), request = request)
        assertEquals(request.method, method.name)
        assertTrue(request.body === body) {
            "It is not the same object!"
        }
    }

    @Test
    fun requestUrlPathSegmentTest() {
        val url = "https://github.com/"
        val pathSegment = "baz"
        val request = request(
            url = url,
            pathSegment = pathSegment
        )
        assertSame(expected = URL("$url$pathSegment"), actual = request.url.toUrl())
        assertHeaders(headers = emptyMap(), request = request)
    }

    @Test
    fun requestUrlHeadersTest() {
        val url = "https://github.com/"
        val headers = mapOf("baz" to "qux")
        val request = request(
            url = url,
            headers = headers
        )
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertHeaders(headers = headers, request = request)
    }

    @Test
    fun requestUrlHeaderTest() {
        val url = "https://github.com/"
        val header = "baz" to "qux"
        val request = request(
            url = url,
            header = header
        )
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertHeaders(headers = mapOf(header), request = request)
    }

    @Test
    fun requestUrlQueriesHeaderTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val header = "baz" to "qux"
        val request = request(
            url = url,
            queries = queries,
            header = header
        )
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertQueries(queries = queries, request = request)
        assertHeaders(headers = mapOf(header), request = request)
    }

    @Test
    fun requestUrlPathSegmentHeadersTest() {
        val url = "https://github.com/"
        val pathSegment = "baz"
        val headers = mapOf("baz" to "qux")
        val request = request(
            url = url,
            pathSegment = pathSegment,
            headers = headers
        )
        assertSame(expected = URL("$url$pathSegment"), actual = request.url.toUrl())
        assertHeaders(headers = headers, request = request)
    }
}
