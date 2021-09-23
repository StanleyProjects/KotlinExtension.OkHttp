package sp.kx.okhttp

import java.util.concurrent.TimeUnit
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

fun okHttpClientBuilder(builder: OkHttpClient.Builder.() -> Unit): OkHttpClient.Builder {
    return OkHttpClient.Builder().also(builder)
}

fun okHttpClient(builder: OkHttpClient.Builder.() -> Unit): OkHttpClient {
    return okHttpClientBuilder(builder).build()
}

fun okHttpClient(
    connectTimeout: Pair<Long, TimeUnit>,
    readTimeout: Pair<Long, TimeUnit>,
    writeTimeout: Pair<Long, TimeUnit>,
    interceptor: (Interceptor.Chain) -> Response
): OkHttpClient {
    return okHttpClient {
        connectTimeout(connectTimeout.first, connectTimeout.second)
        readTimeout(readTimeout.first, readTimeout.second)
        writeTimeout(writeTimeout.first, writeTimeout.second)
        addInterceptor(interceptor)
    }
}

fun OkHttpClient.newCall(
    buildRequest: Request.Builder.() -> Unit
): Call {
    return newCall(request(buildRequest))
}

fun OkHttpClient.newCall(url: String, pathSegment: String): Call {
    return newCall(request(url = url, pathSegment = pathSegment))
}

fun OkHttpClient.newCall(url: String, headers: Map<String, String>): Call {
    return newCall(request(url = url, headers = headers))
}

fun OkHttpClient.newCall(
    url: String,
    queries: Map<String, String>,
    headers: Map<String, String>
): Call {
    return newCall(request(url = url, queries = queries, headers = headers))
}

fun OkHttpClient.newCall(
    url: String,
    queries: Map<String, String>,
    headers: Map<String, String>,
    method: Method,
    body: RequestBody
): Call {
    return newCall(
        request(
            url = url,
            queries = queries,
            headers = headers,
            method = method,
            body = body
        )
    )
}

fun OkHttpClient.newCall(
    url: String,
    queries: Map<String, String>,
    headers: Map<String, String>,
    method: Method
): Call {
    return newCall(
        request(
            url = url,
            queries = queries,
            headers = headers,
            method = method
        )
    )
}

fun OkHttpClient.newCall(
    url: String,
    method: Method,
    body: RequestBody
): Call {
    return newCall(
        request(
            url = url,
            method = method,
            body = body
        )
    )
}

fun OkHttpClient.newCall(
    url: String,
    headers: Map<String, String>,
    method: Method,
    body: RequestBody
): Call {
    return newCall(
        request(
            url = url,
            headers = headers,
            method = method,
            body = body
        )
    )
}

fun OkHttpClient.execute(
    buildRequest: Request.Builder.() -> Unit
): Response {
    return newCall(buildRequest).execute()
}
