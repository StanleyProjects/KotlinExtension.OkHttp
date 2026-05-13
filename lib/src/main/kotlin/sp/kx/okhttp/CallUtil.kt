package sp.kx.okhttp

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

fun OkHttpClient.newCall(
    buildRequest: Request.Builder.() -> Unit
): Call {
    return newCall(request(buildRequest))
}

fun OkHttpClient.newCall(url: String): Call {
    return newCall(request(url = url))
}

fun OkHttpClient.newCall(url: String, pathSegment: String): Call {
    return newCall(request(url = url, pathSegment = pathSegment))
}

fun OkHttpClient.newCall(url: String, headers: Map<String, String>): Call {
    return newCall(request(url = url, headers = headers))
}

fun OkHttpClient.newCall(url: String, header: Pair<String, String>): Call {
    return newCall(request(url = url, header = header))
}

fun OkHttpClient.newCall(url: String, pathSegment: String, header: Pair<String, String>): Call {
    return newCall(request(url = url, pathSegment = pathSegment, header = header))
}

fun OkHttpClient.newCall(url: String, pathSegment: String, headers: Map<String, String>): Call {
    return newCall(request(url = url, pathSegment = pathSegment, headers = headers))
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
    header: Pair<String, String>
): Call {
    return newCall(request(url = url, queries = queries, header = header))
}

fun OkHttpClient.newCall(
    url: String,
    pathSegment: String,
    query: Pair<String, String>,
    headers: Map<String, String>,
    method: Method,
    body: RequestBody
): Call {
    return newCall(
        request(
            url = url,
            pathSegment = pathSegment,
            query = query,
            headers = headers,
            method = method,
            body = body
        )
    )
}

fun OkHttpClient.newCall(
    url: String,
    pathSegment: String,
    query: Pair<String, String>,
    header: Pair<String, String>,
    method: Method,
    body: RequestBody
): Call {
    return newCall(
        request(
            url = url,
            pathSegment = pathSegment,
            query = query,
            header = header,
            method = method,
            body = body
        )
    )
}

fun OkHttpClient.newCall(
    url: String,
    query: Pair<String, String>,
    header: Pair<String, String>,
    method: Method,
    body: RequestBody
): Call {
    return newCall(
        request(
            url = url,
            query = query,
            header = header,
            method = method,
            body = body
        )
    )
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

fun OkHttpClient.newCall(
    url: String,
    header: Pair<String, String>,
    method: Method,
    body: RequestBody
): Call {
    return newCall(
        request(
            url = url,
            header = header,
            method = method,
            body = body
        )
    )
}
