package com.enginebai.base.extensions

import androidx.core.util.PatternsCompat

fun String?.isValidEmail(): Boolean {
    return PatternsCompat.EMAIL_ADDRESS.matcher(this ?: "").matches()
}