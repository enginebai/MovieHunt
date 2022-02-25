package com.enginebai.moviehunt.ui.detail.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.enginebai.moviehunt.resources.MHDimensions
import com.enginebai.moviehunt.resources.MHStyle

@Composable
fun MovieCastWidget(
    avatar: String? = null,
    actorName: String? = null,
    character: String? = null
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(
                data = avatar,
                builder = {
                    transformations(CircleCropTransformation())
                },
            ),
            contentDescription = null,
            modifier = Modifier
                .size(MHDimensions.avatarCast.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.height(MHDimensions.avatarCast.dp)
        ) {
            actorName?.let {
                Text(it, style = MHStyle.subtitle2)
            }
            character?.let {
                Text(it, style = MHStyle.caption)
            }
        }
    }
}

@Composable
@Preview
fun MovieCastWidgetPreview() {
    MovieCastWidget(
        avatar = "https://image.tmdb.org/t/p/w185//fysvehTvU6bE3JgxaOTRfvQJzJ4.jpg",
        actorName = "Gal Gadot",
        character = "The Bishop"
    )
}