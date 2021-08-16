package sp.kx.okhttp

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import okhttp3.RequestBody

fun requestBuilder(builder: Request.Builder.() -> Unit): Request.Builder {
    return Request.Builder().also(builder)
}

fun requestBuilder(url: String, headers: Map<String, String>): Request.Builder {
    return requestBuilder {
        url(url.toHttpUrl())
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
    return requestBuilder {
        url(httpUrl(url = url, queries = queries))
        headers.forEach { (key, value) ->
            addHeader(key, value)
        }
    }
}

fun requestBuilder(
    url: String,
    headers: Map<String, String>,
    method: Method,
    body: RequestBody
): Request.Builder {
    return requestBuilder {
        url(url.toHttpUrl())
        headers.forEach { (key, value) ->
            addHeader(key, value)
        }
        when (method) {
            Method.POST -> post(body)
        }
    }
}

fun requestBuilder(
    url: String,
    queries: Map<String, String>,
    headers: Map<String, String>,
    method: Method,
    body: RequestBody
): Request.Builder {
    return requestBuilder {
        url(httpUrl(url = url, queries = queries))
        headers.forEach { (key, value) ->
            addHeader(key, value)
        }
        when (method) {
            Method.POST -> post(body)
        }
    }
}

fun request(builder: Request.Builder.() -> Unit): Request {
    return Request.Builder().also(builder).build()
}

fun request(url: String, headers: Map<String, String>): Request {
    return requestBuilder(url = url, headers = headers).build()
}

fun request(
    url: String,
    queries: Map<String, String>,
    headers: Map<String, String>
): Request {
    return requestBuilder(url = url, queries = queries, headers = headers).build()
}

fun request(
    url: String,
    headers: Map<String, String>,
    method: Method,
    body: RequestBody
): Request {
    return requestBuilder(
        url = url,
        headers = headers,
        method = method,
        body = body
    ).build()
}

fun request(
    url: String,
    queries: Map<String, String>,
    headers: Map<String, String>,
    method: Method,
    body: RequestBody
): Request {
    return requestBuilder(
        url = url,
        queries = queries,
        headers = headers,
        method = method,
        body = body
    ).build()
}

fun Request.newBuilder(builder: Request.Builder.() -> Unit): Request.Builder {
    return newBuilder().also(builder)
}

fun Request.clone(builder: Request.Builder.() -> Unit): Request {
    return newBuilder(builder).build()
}
