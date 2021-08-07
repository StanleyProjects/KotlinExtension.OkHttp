package sp.kx.okhttp

import java.nio.charset.Charset
import okhttp3.MediaType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

internal class ContentTypeTest {
    private fun assertEquals(type: String, subtype: String, mediaType: MediaType) {
        assertEquals(mediaType.type, type)
        assertEquals(mediaType.subtype, subtype)
        assertEquals(mediaType.toString(), "$type/$subtype")
    }

    private fun assertEquals(type: String, subtype: String, charset: Charset, mediaType: MediaType) {
        assertEquals(mediaType.type, type)
        assertEquals(mediaType.subtype, subtype)
        assertEquals(mediaType.toString(), "$type/$subtype; charset=" + charset.name())
    }

    @Test
    fun applicationJsonTest() {
        assertEquals(
            type = "application",
            subtype = "json",
            mediaType = ContentType.Application.json
        )
    }

    @Test
    fun applicationJsonUTF8Test() {
        val charset = Charset.forName("UTF-8")
        val mediaType = ContentType.Application.json(charset)
        assertEquals(
            type = "application",
            subtype = "json",
            charset = charset,
            mediaType = mediaType
        )
        assertTrue(mediaType === ContentType.Application.json(charset)) {
            "It is not the same object!"
        }
    }

    @Test
    fun textPlainTest() {
        assertEquals(
            type = "text",
            subtype = "plain",
            mediaType = ContentType.Text.plain
        )
    }

    @Test
    fun textPlainUTF8Test() {
        val charset = Charset.forName("UTF-8")
        val mediaType = ContentType.Text.plain(charset)
        assertEquals(
            type = "text",
            subtype = "plain",
            charset = charset,
            mediaType = mediaType
        )
        assertTrue(mediaType === ContentType.Text.plain(charset)) {
            "It is not the same object!"
        }
    }
}
