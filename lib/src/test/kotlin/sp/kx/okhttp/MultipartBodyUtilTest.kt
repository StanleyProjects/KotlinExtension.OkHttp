package sp.kx.okhttp

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
}
