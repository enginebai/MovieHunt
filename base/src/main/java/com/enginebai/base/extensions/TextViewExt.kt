package com.enginebai.base.extensions

import android.widget.TextView

/**
 * Set view visible when text is not null or blank, else set view gone.
 */
fun TextView.setTextWithExistence(s: String?) {
    s?.let { this.text = it }
    setExistence(!s.isNullOrBlank())
}

/**
 * Set view visible when text is not null or blank, else set view invisible.
 */
fun TextView.setTextWithVisibility(s: String?) {
    s?.let { this.text = it }
    setVisible(!s.isNullOrBlank())
}