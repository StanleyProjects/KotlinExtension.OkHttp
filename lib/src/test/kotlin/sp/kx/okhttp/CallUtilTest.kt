package sp.kx.okhttp

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.net.URL

class CallUtilTest {
    companion object {
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
    }

    @Test
    fun newCallBuilderTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val headers = mapOf("baz" to "qux")
        val method = Method.POST
        val body = "body".toRequestBody()
        val call = OkHttpClient().newCall {
            url(httpUrl(url = url, queries = queries))
            headers.forEach { (key, value) ->
                addHeader(key, value)
            }
            when (method) {
                Method.POST -> post(body)
            }
        }
        val request = call.request()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertQueries(queries = queries, request = request)
        assertHeaders(headers = headers, request = request)
        assertEquals(request.method, method.name)
        assertTrue(request.body === body) {
            "It is not the same object!"
        }
    }

    @Test
    fun newCallUrlQueriesHeadersBodyTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val headers = mapOf("baz" to "qux")
        val method = Method.POST
        val body = "body".toRequestBody()
        val call = OkHttpClient().newCall(
            url = url,
            queries = queries,
            headers = headers,
            method = method,
            body = body
        )
        val request = call.request()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertQueries(queries = queries, request = request)
        assertHeaders(headers = headers, request = request)
        assertEquals(request.method, method.name)
        assertTrue(request.body === body) {
            "It is not the same object!"
        }
    }

    @Test
    fun newCallUrlQueriesHeadersMethodTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val headers = mapOf("baz" to "qux")
        val method = Method.POST
        val call = OkHttpClient().newCall(
            url = url,
            queries = queries,
            headers = headers,
            method = method
        )
        val request = call.request()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertQueries(queries = queries, request = request)
        assertHeaders(headers = headers, request = request)
        assertEquals(request.method, method.name)
        val body = assertNotNull(request.body)
        assertEquals(body.contentLength(), 0L)
        assertNull(body.contentType())
    }

    @Test
    fun newCallUrlBodyTest() {
        val url = "https://github.com/"
        val method = Method.POST
        val body = "body".toRequestBody()
        val call = OkHttpClient().newCall(
            url = url,
            method = method,
            body = body
        )
        val request = call.request()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertHeaders(headers = emptyMap(), request = request)
        assertEquals(request.method, method.name)
        assertTrue(request.body === body) {
            "It is not the same object!"
        }
    }

    @Test
    fun newCallUrlHeadersBodyTest() {
        val url = "https://github.com/"
        val headers = mapOf("baz" to "qux")
        val method = Method.POST
        val body = "body".toRequestBody()
        val call = OkHttpClient().newCall(
            url = url,
            headers = headers,
            method = method,
            body = body
        )
        val request = call.request()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertHeaders(headers = headers, request = request)
        assertEquals(request.method, method.name)
        assertTrue(request.body === body) {
            "It is not the same object!"
        }
    }

    @Test
    fun newCallUrlHeaderBodyTest() {
        val url = "https://github.com/"
        val header = "baz" to "qux"
        val method = Method.POST
        val body = "body".toRequestBody()
        val call = OkHttpClient().newCall(
            url = url,
            header = header,
            method = method,
            body = body
        )
        val request = call.request()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertHeaders(headers = mapOf(header), request = request)
        assertEquals(request.method, method.name)
        assertTrue(request.body === body) {
            "It is not the same object!"
        }
    }

    @Test
    fun newCallUrlQueriesHeadersTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val headers = mapOf("baz" to "qux")
        val call = OkHttpClient().newCall(
            url = url,
            queries = queries,
            headers = headers
        )
        val request = call.request()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertQueries(queries = queries, request = request)
        assertHeaders(headers = headers, request = request)
    }

    @Test
    fun newCallUrlQueriesHeaderTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val header = "baz" to "qux"
        val call = OkHttpClient().newCall(
            url = url,
            queries = queries,
            header = header
        )
        val request = call.request()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertQueries(queries = queries, request = request)
        assertHeaders(headers = mapOf(header), request = request)
    }

    @Test
    fun newCallUrlPathSegmentTest() {
        val url = "https://github.com/"
        val pathSegment = "baz"
        val call = OkHttpClient().newCall(
            url = url,
            pathSegment = pathSegment
        )
        val request = call.request()
        assertSame(expected = URL("$url$pathSegment"), actual = request.url.toUrl())
        assertHeaders(headers = emptyMap(), request = request)
    }

    @Test
    fun newCallUrlHeadersTest() {
        val url = "https://github.com/"
        val headers = mapOf("baz" to "qux")
        val call = OkHttpClient().newCall(
            url = url,
            headers = headers
        )
        val request = call.request()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertHeaders(headers = headers, request = request)
    }

    @Test
    fun newCallUrlHeaderTest() {
        val url = "https://github.com/"
        val header = "baz" to "qux"
        val call = OkHttpClient().newCall(
            url = url,
            header = header
        )
        val request = call.request()
        assertSame(expected = URL(url), actual = request.url.toUrl())
        assertHeaders(headers = mapOf(header), request = request)
    }

    @Test
    fun newCallUrlPathSegmentHeadersTest() {
        val url = "https://github.com/"
        val pathSegment = "baz"
        val headers = mapOf("baz" to "qux")
        val call = OkHttpClient().newCall(
            url = url,
            pathSegment = pathSegment,
            headers = headers
        )
        val request = call.request()
        assertSame(expected = URL("$url$pathSegment"), actual = request.url.toUrl())
        assertHeaders(headers = headers, request = request)
    }
}
