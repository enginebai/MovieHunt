package com.enginebai.moviehunt.ui.list

import androidx.annotation.StringRes
import com.enginebai.moviehunt.R

enum class MovieCategory(val key: String, @StringRes val strRes: Int) {
    NOW_PLAYING("now_playing", R.string.now_playing),
    POPULAR("popular", R.string.popular),
    TOP_RATED("top_rated", R.string.top_rated),
    UPCOMING("upcoming", R.string.upcoming);
}