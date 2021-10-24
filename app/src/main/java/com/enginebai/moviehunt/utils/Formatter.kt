package com.enginebai.moviehunt.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.ln
import kotlin.math.pow

fun Int.format(): String {
    // source: https://stackoverflow.com/questions/9769554/how-to-convert-number-into-k-thousands-m-million-and-b-billion-suffix-in-jsp?lq=1
    if (this < 1000) return toString()
    val exp = ln(this.toDouble()).div(ln(1000.0)).toInt()
    return String.format("%.2f %c", this.div(1000.0.pow(exp)), "kMGTPE"[exp - 1])
}

fun Int.formatHourMinutes(): String {
    val hours = this.div(60)
    val minutes = this % 60
    return String.format("%d:%02d", hours, minutes)
}

object DateTimeFormatter {
    private const val API_DATE_FORMAT = "yyyy-MM-dd"
    const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

    fun String.toCalendarOrNull(format: String = API_DATE_FORMAT): Calendar? {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        val date = try {
            dateFormat.parse(this)
        } catch (e: Exception) {
            null
        }
        return date?.let { Calendar.getInstance().apply { time = it } }
    }

    fun Calendar.format(format: String = API_DATE_FORMAT): String? {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        return dateFormat.format(this.time)
    }
}