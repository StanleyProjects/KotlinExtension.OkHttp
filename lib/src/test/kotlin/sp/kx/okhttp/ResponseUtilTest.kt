package sp.kx.okhttp

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class ResponseUtilTest {
    @Test
    fun getCodeAndCloseTest() {
        val code = 500
        val response = Response.Builder()
            .code(code)
            .body("foo".toResponseBody())
            .request(Request.Builder().url("https://github.com/".toHttpUrl()).build())
            .protocol(Protocol.HTTP_1_1)
            .message("bar")
            .build()
        val result = response.getCodeAndClose()
        assertEquals(code, result)
        // todo check is closed
    }
}
