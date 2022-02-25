package com.enginebai.moviehunt.ui.detail.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.resources.MHDimensions

@Composable
fun MovieTrailerWidget(
    thumbnail: String? = null,
    trailerUrl: String? = null,
    onTrailerPlayed: (String) -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(MHDimensions.corner.dp)
    ) {
        Box(modifier = Modifier
            .height(90.dp)
            .clickable {
                trailerUrl?.let { onTrailerPlayed(it) }
            }) {
            Image(
                painter = rememberImagePainter(data = thumbnail),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .aspectRatio(ratio = 16f.div(9)),
                contentScale = ContentScale.Crop,
            )
            Image(
                painter = painterResource(id = R.drawable.ic_video_play),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
@Preview
fun MovieTrailerWidgetPreview() {
    MovieTrailerWidget(
        thumbnail = "https://img.youtube.com/vi/XHk5kCIiGoM/mqdefault.jpg"
    )
}