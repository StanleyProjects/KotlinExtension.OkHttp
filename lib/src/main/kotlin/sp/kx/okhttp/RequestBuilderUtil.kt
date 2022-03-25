package sp.kx.okhttp

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import okio.BufferedSink

private val emptyRequestBody = object : RequestBody() {
    private val array = ByteArray(0)

    override fun contentType(): MediaType? {
        return null
    }

    override fun contentLength(): Long {
        return 0
    }

    override fun writeTo(sink: BufferedSink) {
        sink.write(array, 0, 0)
    }
}

fun requestBuilder(builder: Request.Builder.() -> Unit): Request.Builder {
    return Request.Builder().also(builder)
}

fun requestBuilder(url: String, pathSegment: String): Request.Builder {
    return requestBuilder {
        url(httpUrl(url = url, pathSegment = pathSegment))
    }
}

fun requestBuilder(url: String, headers: Map<String, String>): Request.Builder {
    return requestBuilder {
        url(url.toHttpUrl())
        headers.forEach { (key, value) ->
            addHeader(key, value)
        }
    }
}

fun requestBuilder(url: String, header: Pair<String, String>): Request.Builder {
    return requestBuilder {
        url(url.toHttpUrl())
        val (key, value) = header
        addHeader(key, value)
    }
}

fun requestBuilder(url: String, pathSegment: String, header: Pair<String, String>): Request.Builder {
    return requestBuilder {
        url(httpUrl(url = url, pathSegment = pathSegment))

        val (key, value) = header
        addHeader(key, value)
    }
}

fun requestBuilder(url: String, pathSegment: String, headers: Map<String, String>): Request.Builder {
    return requestBuilder {
        url(httpUrl(url = url, pathSegment = pathSegment))
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
    queries: Map<String, String>,
    header: Pair<String, String>
): Request.Builder {
    return requestBuilder {
        url(httpUrl(url = url, queries = queries))
        val (key, value) = header
        addHeader(key, value)
    }
}

fun requestBuilder(
    url: String,
    method: Method,
    body: RequestBody
): Request.Builder {
    return requestBuilder {
        url(url.toHttpUrl())
        when (method) {
            Method.POST -> post(body)
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
    header: Pair<String, String>,
    method: Method,
    body: RequestBody
): Request.Builder {
    return requestBuilder {
        url(url.toHttpUrl())
        val (key, value) = header
        addHeader(key, value)
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

fun requestBuilder(
    url: String,
    queries: Map<String, String>,
    headers: Map<String, String>,
    method: Method
): Request.Builder {
    return requestBuilder {
        url(httpUrl(url = url, queries = queries))
        headers.forEach { (key, value) ->
            addHeader(key, value)
        }
        when (method) {
            Method.POST -> post(emptyRequestBody)
        }
    }
}
