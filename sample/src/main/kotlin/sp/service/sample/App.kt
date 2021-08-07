package sp.service.sample

import java.util.concurrent.TimeUnit
import sp.kx.okhttp.execute
import sp.kx.okhttp.httpUrl
import sp.kx.okhttp.okHttpClient

fun main() {
    val client = okHttpClient(
        connectTimeout = 10L to TimeUnit.SECONDS,
        readTimeout = 30L to TimeUnit.SECONDS,
        writeTimeout = 30L to TimeUnit.SECONDS,
        interceptor = {
            it.proceed(it.request())
        }
    )
    val response = client.execute {
        url(httpUrl(url = "https://github.com/", queries = mapOf("foo" to "bar")))
    }
    println("response code: " + response.code)
}
