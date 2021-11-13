package com.enginebai.moviehunt.resources

import androidx.compose.foundation.isSystemInDarkTheme
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

object MHThemes {
    val headline6 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )

    val caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )

    val body2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )

    val body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )

    val headline5 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )

    val subtitle2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    )

    val mhTypography = Typography(
        h5 = headline5,
        h6 = headline6,
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