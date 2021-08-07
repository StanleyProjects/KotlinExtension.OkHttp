package sp.service.sample

import java.util.concurrent.TimeUnit
import okhttp3.CacheControl
import sp.kx.okhttp.clone
import sp.kx.okhttp.execute
import sp.kx.okhttp.httpUrl
import sp.kx.okhttp.okHttpClient

fun main() {
    val client = okHttpClient(
        connectTimeout = 10L to TimeUnit.SECONDS,
        readTimeout = 30L to TimeUnit.SECONDS,
        writeTimeout = 30L to TimeUnit.SECONDS,
        interceptor = {
            val request = it.request().clone {
                cacheControl(CacheControl.FORCE_NETWORK)
            }
            it.proceed(request)
        }
    )
    val response = client.execute {
        url(httpUrl(url = "https://github.com/", queries = mapOf("foo" to "bar")))
    }
    println("response code: " + response.code)
}
