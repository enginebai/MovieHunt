package com.enginebai.moviehunt.utils

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
    return String.format("%d: %02d: 00", hours, minutes)
}