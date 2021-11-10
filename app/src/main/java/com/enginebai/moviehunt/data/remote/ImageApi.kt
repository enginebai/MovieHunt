package com.enginebai.moviehunt.data.remote

import com.enginebai.moviehunt.BuildConfig

object ImageApi {
    fun getFullUrl(path: String?, size: ImageSize? = ImageSize.ORIGINAL) =
        "${BuildConfig.IMAGE_API_KEY}${size}/${path}"
}

enum class ImageSize(val path: String) {
    W500("w500"),
    W780("w780"),
    W45("w45"),
    W185("w185"),
    H632("h632"),
    ORIGINAL("original");

    override fun toString() = path
}