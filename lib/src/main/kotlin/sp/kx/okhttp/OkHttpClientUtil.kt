package sp.kx.okhttp

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

fun OkHttpClient.execute(
    buildRequest: Request.Builder.() -> Unit
): Response {
    val requestBuilder = Request.Builder().also(buildRequest)
    val request = requestBuilder.build()
    val call = newCall(request)
    return call.execute()
}

fun OkHttpClient.execute(
    httpUrl: HttpUrl,
    buildHttpUrl: HttpUrl.Builder.() -> Unit,
    buildRequest: Request.Builder.() -> Unit
): Response {
    val httpUrlBuilder = httpUrl.newBuilder().also(buildHttpUrl)
    return execute(
        buildRequest = {
            url(httpUrlBuilder.build())
            also(buildRequest)
        }
    )
}
