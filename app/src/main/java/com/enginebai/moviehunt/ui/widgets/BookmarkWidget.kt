package com.enginebai.moviehunt.ui.widgets

import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.resources.ColorsPalette

@Composable
fun BookmarkButton(
    isBookmark: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    IconToggleButton(
        checked = isBookmark,
        onCheckedChange = onCheckedChange) {
        Icon(
            painter = painterResource(id = if (isBookmark) R.drawable.ic_bookmarked else R.drawable.ic_bookmark),
            contentDescription = null,
            tint = ColorsPalette.colorAccent
        )
    }
}