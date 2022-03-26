package sp.kx.okhttp

import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.net.URL

class ExecuteUtilTest {
    @Test
    fun executeBuilderTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val headers = mapOf("baz" to "qux")
        val method = Method.POST
        val requestBody = "requestBody${hashCode()}".toRequestBody()
        val responseBody = "responseBody${hashCode()}".toResponseBody()
        val responseCode = 200
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                assertSame(expected = URL(url), actual = request.url.toUrl())
                assertQueries(queries = queries, request = request)
                assertHeaders(headers = headers, request = request)
                assertEquals(request.method, method.name)
                assertTrue(request.body === requestBody) { "It is not the same object!" }
                ResponseUtilTest.testResponse(request = request, code = responseCode, body = responseBody)
            }
            .build().execute {
                url(httpUrl(url = url, queries = queries))
                headers.forEach { (key, value) ->
                    addHeader(key, value)
                }
                when (method) {
                    Method.POST -> post(requestBody)
                }
            }.use {
                assertEquals(responseCode, it.code)
                assertTrue(it.body === responseBody) { "It is not the same object!" }
            }
    }

    @Test
    fun executeUrlQueriesHeadersBodyTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val headers = mapOf("baz" to "qux")
        val method = Method.POST
        val requestBody = "requestBody${hashCode()}".toRequestBody()
        val responseBody = "responseBody${hashCode()}".toResponseBody()
        val responseCode = 200
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                assertSame(expected = URL(url), actual = request.url.toUrl())
                assertQueries(queries = queries, request = request)
                assertHeaders(headers = headers, request = request)
                assertEquals(request.method, method.name)
                assertTrue(request.body === requestBody) { "It is not the same object!" }
                ResponseUtilTest.testResponse(request = request, code = responseCode, body = responseBody)
            }
            .build().execute(
                url = url,
                queries = queries,
                headers = headers,
                method = method,
                body = requestBody
            ).use {
                assertEquals(responseCode, it.code)
                assertTrue(it.body === responseBody) { "It is not the same object!" }
            }
    }

    @Test
    fun executeUrlQueriesHeadersMethodTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val headers = mapOf("baz" to "qux")
        val method = Method.POST
        val responseBody = "responseBody${hashCode()}".toResponseBody()
        val responseCode = 200
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                assertSame(expected = URL(url), actual = request.url.toUrl())
                assertQueries(queries = queries, request = request)
                assertHeaders(headers = headers, request = request)
                assertEquals(request.method, method.name)
                val body = assertNotNull(request.body)
                assertEquals(body.contentLength(), 0L)
                assertNull(body.contentType())
                ResponseUtilTest.testResponse(request = request, code = responseCode, body = responseBody)
            }
            .build().execute(
                url = url,
                queries = queries,
                headers = headers,
                method = method
            ).use {
                assertEquals(responseCode, it.code)
                assertTrue(it.body === responseBody) { "It is not the same object!" }
            }
    }

    @Test
    fun executeUrlBodyTest() {
        val url = "https://github.com/"
        val method = Method.POST
        val requestBody = "requestBody${hashCode()}".toRequestBody()
        val responseBody = "responseBody${hashCode()}".toResponseBody()
        val responseCode = 200
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                assertSame(expected = URL(url), actual = request.url.toUrl())
                assertHeaders(headers = emptyMap(), request = request)
                assertEquals(request.method, method.name)
                assertTrue(request.body === requestBody) { "It is not the same object!" }
                ResponseUtilTest.testResponse(request = request, code = responseCode, body = responseBody)
            }
            .build().execute(
                url = url,
                method = method,
                body = requestBody
            ).use {
                assertEquals(responseCode, it.code)
                assertTrue(it.body === responseBody) { "It is not the same object!" }
            }
    }

    @Test
    fun executeUrlHeadersBodyTest() {
        val url = "https://github.com/"
        val headers = mapOf("baz" to "qux")
        val method = Method.POST
        val requestBody = "requestBody${hashCode()}".toRequestBody()
        val responseBody = "responseBody${hashCode()}".toResponseBody()
        val responseCode = 200
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                assertSame(expected = URL(url), actual = request.url.toUrl())
                assertHeaders(headers = headers, request = request)
                assertEquals(request.method, method.name)
                assertTrue(request.body === requestBody) { "It is not the same object!" }
                ResponseUtilTest.testResponse(request = request, code = responseCode, body = responseBody)
            }
            .build().execute(
                url = url,
                headers = headers,
                method = method,
                body = requestBody
            ).use {
                assertEquals(responseCode, it.code)
                assertTrue(it.body === responseBody) { "It is not the same object!" }
            }
    }

    @Test
    fun executeUrlHeaderBodyTest() {
        val url = "https://github.com/"
        val header = "baz" to "qux"
        val method = Method.POST
        val requestBody = "requestBody${hashCode()}".toRequestBody()
        val responseBody = "responseBody${hashCode()}".toResponseBody()
        val responseCode = 200
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                assertSame(expected = URL(url), actual = request.url.toUrl())
                assertHeaders(headers = mapOf(header), request = request)
                assertEquals(request.method, method.name)
                assertTrue(request.body === requestBody) {
                    "It is not the same object!"
                }
                ResponseUtilTest.testResponse(request = request, code = responseCode, body = responseBody)
            }
            .build().execute(
                url = url,
                header = header,
                method = method,
                body = requestBody
            ).use {
                assertEquals(responseCode, it.code)
                assertTrue(it.body === responseBody) { "It is not the same object!" }
            }
    }

    @Test
    fun executeUrlQueriesHeadersTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val headers = mapOf("baz" to "qux")
        val responseBody = "responseBody${hashCode()}".toResponseBody()
        val responseCode = 200
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                assertSame(expected = URL(url), actual = request.url.toUrl())
                assertQueries(queries = queries, request = request)
                assertHeaders(headers = headers, request = request)
                ResponseUtilTest.testResponse(request = request, code = responseCode, body = responseBody)
            }
            .build().execute(
                url = url,
                queries = queries,
                headers = headers
            ).use {
                assertEquals(responseCode, it.code)
                assertTrue(it.body === responseBody) { "It is not the same object!" }
            }
    }

    @Test
    fun executeUrlQueriesHeaderTest() {
        val url = "https://github.com/"
        val queries = mapOf("foo" to "bar")
        val header = "baz" to "qux"
        val responseBody = "responseBody${hashCode()}".toResponseBody()
        val responseCode = 200
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                assertSame(expected = URL(url), actual = request.url.toUrl())
                assertQueries(queries = queries, request = request)
                assertHeaders(headers = mapOf(header), request = request)
                ResponseUtilTest.testResponse(request = request, code = responseCode, body = responseBody)
            }
            .build().execute(
                url = url,
                queries = queries,
                header = header
            ).use {
                assertEquals(responseCode, it.code)
                assertTrue(it.body === responseBody) { "It is not the same object!" }
            }
    }

    @Test
    fun executeUrlPathSegmentTest() {
        val url = "https://github.com/"
        val pathSegment = "baz"
        val responseBody = "responseBody${hashCode()}".toResponseBody()
        val responseCode = 200
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                assertSame(expected = URL("$url$pathSegment"), actual = request.url.toUrl())
                assertHeaders(headers = emptyMap(), request = request)
                ResponseUtilTest.testResponse(request = request, code = responseCode, body = responseBody)
            }
            .build().execute(
                url = url,
                pathSegment = pathSegment
            ).use {
                assertEquals(responseCode, it.code)
                assertTrue(it.body === responseBody) { "It is not the same object!" }
            }
    }
}
