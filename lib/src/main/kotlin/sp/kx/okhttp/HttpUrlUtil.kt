package sp.kx.okhttp

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

fun HttpUrl.build(builder: HttpUrl.Builder.() -> Unit): HttpUrl {
    return newBuilder().also(builder).build()
}

fun httpUrl(url: String, builder: HttpUrl.Builder.() -> Unit): HttpUrl {
    return url.toHttpUrl().build(builder)
}

fun httpUrl(url: String, queries: Map<String, String>): HttpUrl {
    return url.toHttpUrl().build {
        queries.forEach { (key, value) ->
            addQueryParameter(key, value)
        }
    }
}
