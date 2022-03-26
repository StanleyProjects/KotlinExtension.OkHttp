package sp.kx.okhttp

import okhttp3.Request
import okhttp3.RequestBody

fun request(builder: Request.Builder.() -> Unit): Request {
    return Request.Builder().also(builder).build()
}

fun request(url: String, pathSegment: String): Request {
    return requestBuilder(url = url, pathSegment = pathSegment).build()
}

fun request(url: String, headers: Map<String, String>): Request {
    return requestBuilder(url = url, headers = headers).build()
}

fun request(url: String, header: Pair<String, String>): Request {
    return requestBuilder(url = url, header = header).build()
}

fun request(url: String, pathSegment: String, header: Pair<String, String>): Request {
    return requestBuilder(url = url, pathSegment = pathSegment, header = header).build()
}

fun request(url: String, pathSegment: String, headers: Map<String, String>): Request {
    return requestBuilder(url = url, pathSegment = pathSegment, headers = headers).build()
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
    queries: Map<String, String>,
    header: Pair<String, String>
): Request {
    return requestBuilder(url = url, queries = queries, header = header).build()
}

fun request(
    url: String,
    method: Method,
    body: RequestBody
): Request {
    return requestBuilder(
        url = url,
        method = method,
        body = body
    ).build()
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
    header: Pair<String, String>,
    method: Method,
    body: RequestBody
): Request {
    return requestBuilder(
        url = url,
        header = header,
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

fun request(
    url: String,
    queries: Map<String, String>,
    headers: Map<String, String>,
    method: Method
): Request {
    return requestBuilder(
        url = url,
        queries = queries,
        headers = headers,
        method = method
    ).build()
}

fun Request.newBuilder(builder: Request.Builder.() -> Unit): Request.Builder {
    return newBuilder().also(builder)
}

fun Request.clone(builder: Request.Builder.() -> Unit): Request {
    return newBuilder(builder).build()
}
