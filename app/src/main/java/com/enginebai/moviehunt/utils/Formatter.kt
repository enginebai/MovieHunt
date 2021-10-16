package com.enginebai.moviehunt.utils

import java.text.SimpleDateFormat
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

object DateFormat {
    private const val API_DATE_FORMAT = "yyyy-MM-dd"

    fun String.toCalendarOrNull(): Calendar? {
        val dateFormat = SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault())
        val date = try {
            dateFormat.parse(this)
        } catch (e: Exception) {
            null
        }
        return date?.let { Calendar.getInstance().apply { time = it } }
    }

    fun Calendar.yyyyMMdd(): String? {
        val dateFormat = SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(this.time)
    }
}