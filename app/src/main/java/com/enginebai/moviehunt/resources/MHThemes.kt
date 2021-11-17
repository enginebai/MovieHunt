package com.enginebai.moviehunt.resources

import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.enginebai.moviehunt.resources.MHStyle.body1
import com.enginebai.moviehunt.resources.MHStyle.body2
import com.enginebai.moviehunt.resources.MHStyle.caption
import com.enginebai.moviehunt.resources.MHStyle.headline5
import com.enginebai.moviehunt.resources.MHStyle.headline6
import com.enginebai.moviehunt.resources.MHStyle.subtitle2

object MHThemes {

    val mhTypography = Typography(
        h5 = headline5,
        h6 = headline6,
        // default text style when no one specified
        body1 = body1,
        body2 = body2,
        subtitle2 = subtitle2,
        caption = caption
    )

    val mhColorPalette = darkColors(
        primary = ColorsPalette.colorPrimary,
        primaryVariant = ColorsPalette.colorPrimaryDark,
        secondary = ColorsPalette.colorAccent,
        background = MHColors.background,
    )
}

@Composable
fun MovieHuntTheme(
    // TODO: support theme switching
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = MHThemes.mhColorPalette,
        typography = MHThemes.mhTypography,
        shapes = shapes,
        content = content
    )
}