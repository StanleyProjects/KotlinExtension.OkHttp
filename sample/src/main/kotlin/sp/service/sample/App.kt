package sp.service.sample

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import sp.kx.okhttp.execute

fun main() {
    val client = OkHttpClient()
    val response = client.execute(
        httpUrl = "https://github.com/".toHttpUrl(),
        buildHttpUrl = {},
        buildRequest = {}
    )
    println("response code: " + response.code)
}
