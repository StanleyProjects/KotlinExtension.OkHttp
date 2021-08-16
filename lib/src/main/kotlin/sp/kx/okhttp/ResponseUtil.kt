package sp.kx.okhttp

import okhttp3.Response
import okhttp3.ResponseBody

fun Response.getBodyOrThrow(lazyThrowable: () -> Throwable): ResponseBody {
    return body ?: throw lazyThrowable()
}

fun Response.requireBody(lazyMessage: () -> String): ResponseBody {
    return getBodyOrThrow {
        IllegalStateException(lazyMessage())
    }
}

fun Response.requireBody(): ResponseBody {
    return requireBody {
        "Response body null with code $code!"
    }
}

fun Response.getHeaderOrThrow(key: String, lazyThrowable: () -> Throwable): String {
    return headers[key] ?: throw lazyThrowable()
}

fun Response.requireHeader(key: String, lazyMessage: () -> String): String {
    return getHeaderOrThrow(key = key) {
        IllegalStateException(lazyMessage())
    }
}

fun Response.requireHeader(key: String): String {
    return requireHeader(key = key) {
        "Header by key \"$key\" does not exists!"
    }
}

fun Response.getCodeAndClose(): Int {
    val result = code
    close()
    return result
}
