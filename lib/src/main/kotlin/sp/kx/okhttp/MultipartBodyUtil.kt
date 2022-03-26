package sp.kx.okhttp

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun multipartBodyBuilder(
    builder: MultipartBody.Builder.() -> Unit
): MultipartBody.Builder {
    return MultipartBody.Builder().also(builder)
}

fun MultipartBody.Builder.addFormDataPart(key: String, requestBody: RequestBody) {
    addFormDataPart(key, null, requestBody)
}

fun MultipartBody.Builder.addFormDataPart(key: String, mediaType: MediaType, value: ByteArray) {
    addFormDataPart(key, value.toRequestBody(mediaType))
}

fun formDataBuilder(
    key: String,
    requestBody: RequestBody
): MultipartBody.Builder {
    return multipartBodyBuilder {
        setType(MultipartBody.FORM)
        addFormDataPart(key = key, requestBody)
    }
}

fun formDataBuilder(
    key: String,
    mediaType: MediaType,
    bytes: ByteArray
): MultipartBody.Builder {
    return formDataBuilder(key = key, bytes.toRequestBody(mediaType))
}

fun multipartBody(
    builder: MultipartBody.Builder.() -> Unit
): MultipartBody {
    return multipartBodyBuilder(builder).build()
}

fun formDataBody(
    key: String,
    requestBody: RequestBody
): MultipartBody {
    return formDataBuilder(key = key, requestBody = requestBody).build()
}

fun formDataBody(
    key: String,
    mediaType: MediaType,
    bytes: ByteArray
): MultipartBody {
    return formDataBuilder(key = key, mediaType = mediaType, bytes = bytes).build()
}
