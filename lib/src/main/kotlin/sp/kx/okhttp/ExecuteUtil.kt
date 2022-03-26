package sp.kx.okhttp

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

fun OkHttpClient.execute(
    buildRequest: Request.Builder.() -> Unit
): Response {
    return newCall(buildRequest).execute()
}

fun OkHttpClient.execute(url: String, pathSegment: String): Response {
    return newCall(url = url, pathSegment = pathSegment).execute()
}

fun OkHttpClient.execute(url: String, headers: Map<String, String>): Response {
    return newCall(url = url, headers = headers).execute()
}

fun OkHttpClient.execute(url: String, header: Pair<String, String>): Response {
    return newCall(url = url, header = header).execute()
}

fun OkHttpClient.execute(url: String, pathSegment: String, header: Pair<String, String>): Response {
    return newCall(url = url, pathSegment = pathSegment, header = header).execute()
}

fun OkHttpClient.execute(url: String, pathSegment: String, headers: Map<String, String>): Response {
    return newCall(url = url, pathSegment = pathSegment, headers = headers).execute()
}

fun OkHttpClient.execute(
    url: String,
    queries: Map<String, String>,
    headers: Map<String, String>
): Response {
    return newCall(url = url, queries = queries, headers = headers).execute()
}

fun OkHttpClient.execute(
    url: String,
    queries: Map<String, String>,
    header: Pair<String, String>
): Response {
    return newCall(url = url, queries = queries, header = header).execute()
}

fun OkHttpClient.execute(
    url: String,
    queries: Map<String, String>,
    headers: Map<String, String>,
    method: Method,
    body: RequestBody
): Response {
    return newCall(
        url = url,
        queries = queries,
        headers = headers,
        method = method,
        body = body
    ).execute()
}

fun OkHttpClient.execute(
    url: String,
    queries: Map<String, String>,
    headers: Map<String, String>,
    method: Method
): Response {
    return newCall(
        url = url,
        queries = queries,
        headers = headers,
        method = method
    ).execute()
}

fun OkHttpClient.execute(
    url: String,
    method: Method,
    body: RequestBody
): Response {
    return newCall(
        url = url,
        method = method,
        body = body
    ).execute()
}

fun OkHttpClient.execute(
    url: String,
    headers: Map<String, String>,
    method: Method,
    body: RequestBody
): Response {
    return newCall(
        url = url,
        headers = headers,
        method = method,
        body = body
    ).execute()
}

fun OkHttpClient.execute(
    url: String,
    header: Pair<String, String>,
    method: Method,
    body: RequestBody
): Response {
    return newCall(
        url = url,
        header = header,
        method = method,
        body = body
    ).execute()
}
