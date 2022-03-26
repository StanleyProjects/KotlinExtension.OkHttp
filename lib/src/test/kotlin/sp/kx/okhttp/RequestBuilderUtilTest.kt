package sp.kx.okhttp

import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.net.URL

internal class RequestBuilderUtilTest {
    @Test
    fun requestBuilderTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val headers = mapOf("baz" to "qux")
        val method = Method.POST
        val body = "body".toRequestBody()
        val request = requestBuilder {
            url(httpUrl(url = url, queries = queries))
            headers.forEach { (key, value) ->
                addHeader(key, value)
            }
            when (method) {
                Method.POST -> post(body)
            }
        }.build()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertQueries(queries = queries, request = request)
        assertHeaders(headers = headers, request = request)
        assertEquals(request.method, method.name)
        assertTrue(request.body === body) {
            "It is not the same object!"
        }
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
    fun requestBuilderUrlQueriesHeadersMethodTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val headers = mapOf("baz" to "qux")
        val method = Method.POST
        val request = requestBuilder(
            url = url,
            queries = queries,
            headers = headers,
            method = method
        ).build()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertQueries(queries = queries, request = request)
        assertHeaders(headers = headers, request = request)
        assertEquals(request.method, method.name)
        val body = assertNotNull(request.body)
        assertEquals(body.contentLength(), 0L)
        assertNull(body.contentType())
    }

    @Test
    fun requestBuilderUrlBodyTest() {
        val url = "https://github.com/"
        val method = Method.POST
        val body = "body".toRequestBody()
        val request = requestBuilder(
            url = url,
            method = method,
            body = body
        ).build()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertHeaders(headers = emptyMap(), request = request)
        assertEquals(request.method, method.name)
        assertTrue(request.body === body) {
            "It is not the same object!"
        }
    }

    @Test
    fun requestBuilderUrlHeadersBodyTest() {
        val url = "https://github.com/"
        val headers = mapOf("baz" to "qux")
        val method = Method.POST
        val body = "body".toRequestBody()
        val request = requestBuilder(
            url = url,
            headers = headers,
            method = method,
            body = body
        ).build()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertHeaders(headers = headers, request = request)
        assertEquals(request.method, method.name)
        assertTrue(request.body === body) {
            "It is not the same object!"
        }
    }

    @Test
    fun requestBuilderUrlHeaderBodyTest() {
        val url = "https://github.com/"
        val header = "baz" to "qux"
        val method = Method.POST
        val body = "body".toRequestBody()
        val request = requestBuilder(
            url = url,
            header = header,
            method = method,
            body = body
        ).build()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertHeaders(headers = mapOf(header), request = request)
        assertEquals(request.method, method.name)
        assertTrue(request.body === body) {
            "It is not the same object!"
        }
    }

    @Test
    fun requestBuilderUrlPathSegmentTest() {
        val url = "https://github.com/"
        val pathSegment = "baz"
        val request = requestBuilder(
            url = url,
            pathSegment = pathSegment
        ).build()
        assertSame(expected = URL("$url$pathSegment"), actual = request.url.toUrl())
        assertHeaders(headers = emptyMap(), request = request)
    }

    @Test
    fun requestBuilderUrlPathSegmentHeaderTest() {
        TODO()
    }

    @Test
    fun requestBuilderUrlHeadersTest() {
        val url = "https://github.com/"
        val headers = mapOf("baz" to "qux")
        val request = requestBuilder(
            url = url,
            headers = headers
        ).build()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertHeaders(headers = headers, request = request)
    }

    @Test
    fun requestBuilderUrlHeaderTest() {
        val url = "https://github.com/"
        val header = "baz" to "qux"
        val request = requestBuilder(
            url = url,
            header = header
        ).build()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertHeaders(headers = mapOf(header), request = request)
    }

    @Test
    fun requestBuilderUrlPathSegmentHeadersTest() {
        val url = "https://github.com/"
        val pathSegment = "baz"
        val headers = mapOf("baz" to "qux")
        val request = requestBuilder(
            url = url,
            pathSegment = pathSegment,
            headers = headers
        ).build()
        assertSame(expected = URL("$url$pathSegment"), actual = request.url.toUrl())
        assertHeaders(headers = headers, request = request)
    }

    @Test
    fun requestBuilderUrlQueriesHeadersTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val headers = mapOf("baz" to "qux")
        val request = requestBuilder(
            url = url,
            queries = queries,
            headers = headers
        ).build()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertQueries(queries = queries, request = request)
        assertHeaders(headers = headers, request = request)
    }

    @Test
    fun requestBuilderUrlQueriesHeaderTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val header = "baz" to "qux"
        val request = requestBuilder(
            url = url,
            queries = queries,
            header = header
        ).build()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertQueries(queries = queries, request = request)
        assertHeaders(headers = mapOf(header), request = request)
    }
}
