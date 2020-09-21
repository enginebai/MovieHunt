package com.enginebai.base.extensions

import android.view.View
import android.view.ViewGroup

fun ViewGroup.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

fun ViewGroup.setExistence(exist: Boolean) {
    visibility = if (exist) View.VISIBLE else View.GONE
}

fun ViewGroup.gone() {
    setExistence(false)
}

fun ViewGroup.invisible() {
    setVisible(false)
}

fun ViewGroup.visible() {
    visibility = View.VISIBLE
}

fun ViewGroup.toggleVisible() {
    visibility = if (visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
}

fun ViewGroup.toggleExistence() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}

val ViewGroup.isGone: Boolean get() = (visibility == View.GONE)
val ViewGroup.isInvisible: Boolean get() = (visibility == View.INVISIBLE)
val ViewGroup.isVisible: Boolean get() = (visibility == View.VISIBLE)

