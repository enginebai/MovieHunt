package com.enginebai.moviehunt.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

object BindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "placeholder", "error"], requireAll = false)
    fun loadImage(
        imageView: ImageView,
        imageUrl: String?,
        placeholder: Drawable?,
        error: Drawable?
    ) {
        imageView
            .load(imageUrl) {
                placeholder(placeholder)
                error(error)
            }
    }

}