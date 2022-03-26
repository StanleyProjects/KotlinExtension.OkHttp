package sp.kx.okhttp

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

fun HttpUrl.newBuilder(builder: HttpUrl.Builder.() -> Unit): HttpUrl.Builder {
    return newBuilder().also(builder)
}

fun HttpUrl.build(builder: HttpUrl.Builder.() -> Unit): HttpUrl {
    return newBuilder(builder).build()
}

fun httpUrl(url: String, queries: Map<String, String>): HttpUrl {
    return url.toHttpUrl().build {
        queries.forEach { (key, value) ->
            addQueryParameter(key, value)
        }
    }
}

fun httpUrl(url: String, pathSegment: String): HttpUrl {
    return url.toHttpUrl().build {
        addPathSegment(pathSegment)
    }
}

fun HttpUrl.clone(builder: HttpUrl.Builder.() -> Unit): HttpUrl {
    return newBuilder(builder).build()
}
