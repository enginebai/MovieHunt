package com.enginebai.base.extensions

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import retrofit2.Converter
import retrofit2.Retrofit
import timber.log.Timber
import java.lang.reflect.Type

/**
 * Enable to serialize enum with Gson @SerializedName annotation to Retrofit HTTP query string.
 *
 * Example:
 *  enum class MediaType {
 *      @SerializedName("jpeg")
 *      IMAGE_JPG,
 *      @SerializedName("mp4")
 *      VIDEO_MP4
 *  }
 *
 *  interface ApiService {
 *      @GET
 *      fun getMediaList(@Query("type") mediaType: MediaType): List<Media>
 *  }
 *
 *  val mediaList = apiService.getMediaList(MediaType.IMAGE_JPG)
 *
 * The actual HTTP request will be GET /medias?type=image-jpeg
 */
object EnumGsonSerializedNameConverterFactory : Converter.Factory() {
    override fun stringConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        if (type is Class<*> && type.isEnum) {
            return Converter<Any?, String> { value -> getSerializedNameValue(value as Enum<*>) }
        }
        return null
    }

    private fun <E : Enum<*>> getSerializedNameValue(e: E): String {
        val exception = IllegalStateException(
            "You might miss the Gson @SerializedName annotation for " +
                    "your enum class $e that is used for Retrofit request/response"
        )
        return e.javaClass.getField(e.name).getAnnotation(SerializedName::class.java)?.value
            ?: throw exception
    }
}

object EnumHasValueConverterFactory: Converter.Factory() {
    override fun stringConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        if (type is Class<*> && type.isEnum && type.interfaces.contains(EnumHasValue::class.java)) {
            return Converter<Any?, String> { value -> (value as EnumHasValue).value }
        }
        return null
    }
}

// source: https://stackoverflow.com/a/45561053/2279285
interface EnumHasValue {
    val value: String
}

class EnumHasValueJsonAdapter<T> : JsonSerializer<T>, JsonDeserializer<T> where T : EnumHasValue {
    override fun serialize(
        src: T,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.value)
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): T? {
        val parsedValue = (typeOfT as Class<T>).enumConstants?.associateBy { it.value }?.get(json.asString)
        parsedValue ?: kotlin.run { Timber.w("Can not deserialize value ${json.asString} to $typeOfT") }
        return parsedValue
    }

}

/**
 * Serialize or deserialize the enum by ordinal.
 *
 * Suppose we have a field representing order status:
 *  0: reservation success, 1: arrival, 2: cancel
 *
 * We define a enum for this field and we can use json adapter:
 *
 * @JsonAdapter(EnumOrdinalJsonAdapter::class)
 * enum class OrderStatus {
 *    RESERVATION_SUCCESS,
 *    ARRIVAL,
 *    CANCEL
 * }
 *
 * data class Order(
 *    val status: OrderStatus?
 * )
 */
class EnumOrdinalJsonAdapter<T> : JsonSerializer<T>, JsonDeserializer<T> where T : Enum<T> {
    override fun serialize(
        src: T,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.ordinal)
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): T? {
        val parsedValue = (typeOfT as Class<T>).enumConstants?.associateBy { it.ordinal }?.get(json.asInt)
        parsedValue ?: kotlin.run { Timber.w("Can not deserialize value ${json.asString} to $typeOfT") }
        return parsedValue
    }
}