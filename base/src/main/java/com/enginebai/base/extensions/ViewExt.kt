package com.enginebai.base.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * Prevent multiple click in a short period of time. Default interval is 1500 milli-second.
 * @param intervalInMillis: the time interval to trigger next click events.
 * @param listener: the click listener.
 */
inline fun View.debounceClick(
    intervalInMillis: Int = 1500,
    crossinline listener: (view: View) -> Unit
) {
    var lastClick = 0L
    setOnClickListener {
        val diff = System.currentTimeMillis() - lastClick
        lastClick = System.currentTimeMillis()
        if (diff > intervalInMillis) {
            listener(it)
        }
    }
}

//
// visibility
//
/**
 * Set visible or invisible of view.
 */
fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

/**
 * Set visible or gone of view.
 */
fun View.setExistence(exist: Boolean) {
    visibility = if (exist) View.VISIBLE else View.GONE
}

/**
 * Set view visible when matching the given [predicate]
 */
inline fun View.showIf(predicate: () -> Boolean): View {
    if (visibility != View.VISIBLE && predicate()) {
        visibility = View.VISIBLE
    }
    return this
}

/**
 * Set view invisible when matching the given [predicate]
 */
inline fun View.hideIf(predicate: () -> Boolean): View {
    if (visibility != View.INVISIBLE && predicate()) {
        visibility = View.INVISIBLE
    }
    return this
}

/**
 * Set view gone when matching the given [predicate]
 */
inline fun View.goneIf(predicate: () -> Boolean): View {
    if (visibility != View.GONE && predicate()) {
        visibility = View.GONE
    }
    return this
}

fun View.gone() {
    setExistence(false)
}

fun View.invisible() {
    setVisible(false)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.toggleVisible() {
    visibility = if (visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
}

fun View.toggleExistence() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}

val View.isGone: Boolean get() = (visibility == View.GONE)
val View.isInvisible: Boolean get() = (visibility == View.INVISIBLE)
val View.isVisible: Boolean get() = (visibility == View.VISIBLE)

//
// keyboard
//
fun View.showKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

//
// dimension conversion
//
fun View.px2dp(px: Float): Int {
    return (px / resources.displayMetrics.density + 0.5f).toInt()
}

fun View.dp2px(dp: Float): Int {
    return (dp * resources.displayMetrics.density + 0.5f).toInt()
}

fun View.px2sp(px: Float): Int {
    return (px / resources.displayMetrics.scaledDensity + 0.5f).toInt()
}

fun View.sp2px(sp: Float): Int {
    return (sp * resources.displayMetrics.scaledDensity + 0.5f).toInt()
}

//
// resources
//
fun View.getColor(@ColorRes resId: Int) = ContextCompat.getColor(context, resId)
fun View.getDrawable(@DrawableRes resId: Int) = ContextCompat.getDrawable(context, resId)
fun View.getDrawableWithIntrinsicSize(@DrawableRes resId: Int): Drawable? {
    return getDrawable(resId)?.apply {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
    }
}