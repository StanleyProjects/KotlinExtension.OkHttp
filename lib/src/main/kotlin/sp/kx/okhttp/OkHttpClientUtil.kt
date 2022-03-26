package sp.kx.okhttp

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.util.concurrent.TimeUnit

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
