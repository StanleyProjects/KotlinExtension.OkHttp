package sp.kx.okhttp

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun multipartBodyBuilder(
    builder: MultipartBody.Builder.() -> Unit
): MultipartBody.Builder {
    return MultipartBody.Builder().also(builder)
}

fun formDataBuilder(
    key: String,
    filename: String,
    body: RequestBody
): MultipartBody.Builder {
    return multipartBodyBuilder {
        setType(MultipartBody.FORM)
        addFormDataPart(name = key, filename = filename, body = body)
    }
}

fun formDataBuilder(
    key: String,
    file: File,
    filename: String = file.name
): MultipartBody.Builder {
    return formDataBuilder(key = key, filename = filename, file.readBytes().toRequestBody())
}

fun formDataBody(
    key: String,
    file: File,
    filename: String = file.name
): MultipartBody {
    return formDataBuilder(key = key, file = file, filename = filename).build()
}
