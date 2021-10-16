package com.enginebai.moviehunt.utils

import com.enginebai.moviehunt.utils.DateTimeFormatter.toCalendarOrNull
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.*

object CalendarDeserializer : JsonDeserializer<Calendar> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Calendar? {
        return json?.asString?.toCalendarOrNull()
    }
}