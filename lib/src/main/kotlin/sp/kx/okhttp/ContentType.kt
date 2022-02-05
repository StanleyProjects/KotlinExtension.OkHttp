package sp.kx.okhttp

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import java.nio.charset.Charset

object ContentType {
    private val mediaTypes: MutableMap<String, MediaType> = mutableMapOf()

    private fun getMediaType(key: String): MediaType {
        return mediaTypes.getOrPut(key) {
            key.toMediaType()
        }
    }

    private fun getMediaType(type: String, subtype: String): MediaType {
        return getMediaType("$type/$subtype")
    }

    private fun getMediaType(type: String, subtype: String, charset: Charset): MediaType {
        return getMediaType("$type/$subtype; charset=" + charset.name())
    }

    object Application {
        private const val type = "application"

        val json: MediaType = "$type/json".toMediaType()
        fun json(charset: Charset): MediaType {
            return getMediaType(type = type, subtype = "json", charset = charset)
        }
    }

    object Text {
        private const val type = "text"

        val plain: MediaType = "$type/plain".toMediaType()
        fun plain(charset: Charset): MediaType {
            return getMediaType(type = type, subtype = "plain", charset = charset)
        }
    }
}
