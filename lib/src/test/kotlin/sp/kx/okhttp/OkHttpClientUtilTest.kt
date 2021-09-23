package sp.kx.okhttp

import java.net.URL
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue

internal class OkHttpClientUtilTest {
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
    fun okHttpClientBuilderTest() {
        val timeout: Long = 10
        val unit: TimeUnit = TimeUnit.SECONDS
        val builder: OkHttpClient.Builder = okHttpClientBuilder {
            connectTimeout(timeout, unit)
        }
        val client = builder.build()
        assertEquals(client.connectTimeoutMillis.toLong(), unit.toMillis(timeout))
        assertEquals(client.interceptors.size, 0)
    }

    @Test
    fun okHttpClientTest() {
        val timeout: Long = 10
        val unit: TimeUnit = TimeUnit.SECONDS
        val client: OkHttpClient = okHttpClient {
            connectTimeout(timeout, unit)
        }
        assertEquals(client.connectTimeoutMillis.toLong(), unit.toMillis(timeout))
        assertEquals(client.interceptors.size, 0)
    }

    @Test
    fun okHttpClientInterceptorTest() {
        val connectTimeout: Long = 10
        val connectUnit: TimeUnit = TimeUnit.SECONDS
        val readTimeout: Long = 11_000
        val readUnit: TimeUnit = TimeUnit.MILLISECONDS
        val writeTimeout: Long = 1
        val writeUnit: TimeUnit = TimeUnit.MINUTES
        val client: OkHttpClient = okHttpClient(
            connectTimeout = connectTimeout to connectUnit,
            readTimeout = readTimeout to readUnit,
            writeTimeout = writeTimeout to writeUnit,
            interceptor = {
                Response.Builder().build()
            }
        )
        assertEquals(client.connectTimeoutMillis.toLong(), connectUnit.toMillis(connectTimeout))
        assertEquals(client.readTimeoutMillis.toLong(), readUnit.toMillis(readTimeout))
        assertEquals(client.writeTimeoutMillis.toLong(), writeUnit.toMillis(writeTimeout))
        assertEquals(client.interceptors.size, 1) // todo
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
}
