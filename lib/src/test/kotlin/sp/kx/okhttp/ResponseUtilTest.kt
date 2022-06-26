package sp.kx.okhttp

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import sp.kx.okhttp.util.org.junit.assertType

internal class ResponseUtilTest {
    companion object {
        fun testResponse(
            request: Request,
            code: Int = 200,
            body: ResponseBody = "test_response".toResponseBody(),
            protocol: Protocol = Protocol.HTTP_1_1,
            message: String = "test response message"
        ): Response {
            return Response.Builder()
                .protocol(protocol)
                .message(message)
                .request(request)
                .code(code)
                .body(body)
                .build()
        }
    }

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

    @Test
    fun getBodyOrThrowTest() {
        val response = Response.Builder()
            .code(500)
            .request(Request.Builder().url("https://github.com/".toHttpUrl()).build())
            .protocol(Protocol.HTTP_1_1)
            .message("bar")
            .build()
        val throwable = IllegalStateException("getBodyOrThrowTest")
        try {
            response.getBodyOrThrow { throwable }
        } catch (e: Throwable) {
            assertEquals(e, throwable)
            return
        }
        fail<Any?>("No throwable!")
    }

    @Test
    fun getBodyOrThrowSuccessTest() {
        val body = "foo".toResponseBody()
        val response = Response.Builder()
            .code(500)
            .body(body)
            .request(Request.Builder().url("https://github.com/".toHttpUrl()).build())
            .protocol(Protocol.HTTP_1_1)
            .message("bar")
            .build()
        val throwable = IllegalStateException("getBodyOrThrowTest")
        assertEquals(body, response.getBodyOrThrow { throwable })
    }

    @Test
    fun requireBodyMessageTest() {
        val response = Response.Builder()
            .code(500)
            .request(Request.Builder().url("https://github.com/".toHttpUrl()).build())
            .protocol(Protocol.HTTP_1_1)
            .message("bar")
            .build()
        val message = "baz"
        try {
            response.requireBody { message }
        } catch (e: Throwable) {
            assertEquals(e.message, message)
            return
        }
        fail<Any?>("No throwable!")
    }

    @Test
    fun requireBodyTest() {
        val response = Response.Builder()
            .code(500)
            .request(Request.Builder().url("https://github.com/".toHttpUrl()).build())
            .protocol(Protocol.HTTP_1_1)
            .message("bar")
            .build()
        try {
            response.requireBody()
        } catch (e: Throwable) {
            e.assertType<IllegalStateException>()
            return
        }
        fail<Any?>("No throwable!")
    }

    @Test
    fun getHeaderOrThrowTest() {
        val response = Response.Builder()
            .code(500)
            .request(Request.Builder().url("https://github.com/".toHttpUrl()).build())
            .protocol(Protocol.HTTP_1_1)
            .message("bar")
            .build()
        val throwable = IllegalStateException("getBodyOrThrowTest")
        try {
            response.getHeaderOrThrow("foo") { throwable }
        } catch (e: Throwable) {
            assertEquals(e, throwable)
            return
        }
        fail<Any?>("No throwable!")
    }

    @Test
    fun getHeaderOrThrowSuccessTest() {
        val headerKey = "foo"
        val headerValue = "baz"
        val response = Response.Builder()
            .code(500)
            .request(Request.Builder().url("https://github.com/".toHttpUrl()).build())
            .protocol(Protocol.HTTP_1_1)
            .message("bar")
            .header(headerKey, headerValue)
            .build()
        val throwable = IllegalStateException("getBodyOrThrowTest")
        assertEquals(headerValue, response.getHeaderOrThrow(headerKey) { throwable })
    }

    @Test
    fun requireHeaderMessageTest() {
        val response = Response.Builder()
            .code(500)
            .request(Request.Builder().url("https://github.com/".toHttpUrl()).build())
            .protocol(Protocol.HTTP_1_1)
            .message("bar")
            .build()
        val message = "baz"
        try {
            response.requireHeader("foo") { message }
        } catch (e: Throwable) {
            assertEquals(e.message, message)
            return
        }
        fail<Any?>("No throwable!")
    }

    @Test
    fun requireHeaderTest() {
        val response = Response.Builder()
            .code(500)
            .request(Request.Builder().url("https://github.com/".toHttpUrl()).build())
            .protocol(Protocol.HTTP_1_1)
            .message("bar")
            .build()
        try {
            response.requireHeader("foo")
        } catch (e: Throwable) {
            e.assertType<IllegalStateException>()
            return
        }
        fail<Any?>("No throwable!")
    }
}
