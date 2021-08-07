package sp.kx.okhttp

import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.Response
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class OkHttpClientUtilTest {
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
}
