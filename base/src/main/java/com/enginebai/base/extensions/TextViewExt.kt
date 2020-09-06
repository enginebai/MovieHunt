package com.enginebai.base.extensions

import android.view.View
import android.widget.TextView

fun TextView.setTextWithExistence(s: String?) {
	s?.let { this.text = it }
	setExistence(!s.isNullOrBlank())
}

fun TextView.setTextWithVisibility(s: String?) {
	s?.let { this.text = it }
	setVisible(!s.isNullOrBlank())
}