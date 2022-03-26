package sp.kx.okhttp

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class MultipartBodyUtilTest {
    @Test
    fun multipartBodyBuilderTest() {
        val type = MultipartBody.FORM
        val body = "body".toRequestBody()
        val multipartBody = multipartBodyBuilder {
            setType(type)
            addPart(body)
        }.build()
        assertEquals(multipartBody.type, type)
        val parts = multipartBody.parts
        assertEquals(parts.size, 1)
        assertTrue(parts.first().body === body) {
            "It is not the same object!"
        }
    }

    @Test
    fun multipartBodyTest() {
        val type = MultipartBody.FORM
        val body = "body".toRequestBody()
        val multipartBody = multipartBody {
            setType(type)
            addPart(body)
        }
        assertEquals(multipartBody.type, type)
        val parts = multipartBody.parts
        assertEquals(parts.size, 1)
        assertTrue(parts.first().body === body) {
            "It is not the same object!"
        }
    }

    @Test
    fun addFormDataPartTest() {
        val key = "foo"
        val body = "body".toRequestBody()
        val multipartBody = multipartBodyBuilder {
            addFormDataPart(key, body)
        }.build()
        val parts = multipartBody.parts
        assertEquals(1, parts.size)
        val part = parts.first()
        assertTrue(part.body === body) {
            "It is not the same object!"
        }
        val headers = assertNotNull(part.headers).toMultimap()
        assertEquals(1, headers.size)
        val header = assertNotNull(headers["content-disposition"])
        assertEquals(1, header.size)
        assertTrue(header == listOf("form-data; name=\"$key\""))
    }

    @Test
    fun addFormDataPartByteArrayTest() {
        val key = "foo"
        val mediaType = "test/plain".toMediaType()
        val bytes = "bar".toByteArray()
        val multipartBody = multipartBodyBuilder {
            addFormDataPart(key, mediaType, bytes)
        }.build()
        val parts = multipartBody.parts
        assertEquals(1, parts.size)
        val part = parts.first()
        assertEquals(mediaType, part.body.contentType())
        assertEquals(bytes.size.toLong(), part.body.contentLength())
        val headers = assertNotNull(part.headers).toMultimap()
        assertEquals(1, headers.size)
        val header = assertNotNull(headers["content-disposition"])
        assertEquals(1, header.size)
        assertTrue(header == listOf("form-data; name=\"$key\""))
    }
}
