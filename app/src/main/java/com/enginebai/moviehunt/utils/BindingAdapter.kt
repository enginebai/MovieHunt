package com.enginebai.moviehunt.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "placeholder", "error"], requireAll = false)
    fun loadImage(
        imageView: ImageView,
        imageUrl: String?,
        placeholder: Drawable?,
        error: Drawable?
    ) {
        Glide.with(imageView)
            .load(imageUrl)
            .placeholder(placeholder)
            .error(error)
            .into(imageView)
    }

}