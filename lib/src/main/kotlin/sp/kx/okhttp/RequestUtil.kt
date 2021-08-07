package sp.kx.okhttp

import okhttp3.HttpUrl
import okhttp3.Request

fun requestBuilder(builder: Request.Builder.() -> Unit): Request.Builder {
    return Request.Builder().also(builder)
}

fun requestBuilder(httpUrl: HttpUrl, headers: Map<String, String>): Request.Builder {
    return requestBuilder {
        url(httpUrl)
        headers.forEach { (key, value) ->
            addHeader(key, value)
        }
    }
}

fun requestBuilder(
    url: String,
    queries: Map<String, String>,
    headers: Map<String, String>
): Request.Builder {
    return requestBuilder(
        httpUrl = httpUrl(url) {
            queries.forEach { (key, value) ->
                addQueryParameter(key, value)
            }
        },
        headers = headers
    )
}

fun request(builder: Request.Builder.() -> Unit): Request {
    return Request.Builder().also(builder).build()
}

fun request(httpUrl: HttpUrl, headers: Map<String, String>): Request {
    return requestBuilder(httpUrl, headers = headers).build()
}

fun request(
    url: String,
    queries: Map<String, String>,
    headers: Map<String, String>
): Request {
    return requestBuilder(url = url, queries = queries, headers = headers).build()
}

fun Request.newBuilder(builder: Request.Builder.() -> Unit): Request.Builder {
    return newBuilder().also(builder)
}

fun Request.clone(builder: Request.Builder.() -> Unit): Request {
    return newBuilder(builder).build()
}
