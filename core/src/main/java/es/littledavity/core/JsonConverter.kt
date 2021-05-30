package es.littledavity.core

import es.littledavity.core.utils.decodeFromStringOrNull
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Singleton
class JsonConverter @Inject constructor(val json: Json) {

    inline fun <reified T> toJson(clazz: T): String {
        return this.json.encodeToString(clazz)
    }

    inline fun <reified T> fromJson(json: String): T? {
        return this.json.decodeFromStringOrNull(json)
    }
}