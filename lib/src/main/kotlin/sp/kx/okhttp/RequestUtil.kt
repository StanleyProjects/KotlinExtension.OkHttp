package sp.kx.okhttp

import okhttp3.Request

fun requestBuilder(builder: Request.Builder.() -> Unit): Request.Builder {
    return Request.Builder().also(builder)
}

fun requestBuilder(headers: Map<String, String>): Request.Builder {
    return requestBuilder {
        headers.forEach { (key, value) ->
            addHeader(key, value)
        }
    }
}

fun request(builder: Request.Builder.() -> Unit): Request {
    return Request.Builder().also(builder).build()
}

fun request(headers: Map<String, String>): Request {
    return requestBuilder(headers).build()
}
