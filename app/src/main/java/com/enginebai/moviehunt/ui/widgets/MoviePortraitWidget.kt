package com.enginebai.moviehunt.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.resources.MHColors
import com.enginebai.moviehunt.resources.MHDimensions
import com.enginebai.moviehunt.resources.MHStyle

@Composable
fun MoviePortraitWidget(
    movieId: String,
    posterUrl: String?,
    movieName: String?,
    releaseYear: Int? = null,
    genre: String? = null,
    rating: Float? = null,
    ratingTotalCountText: String? = null,
    onClickListener: (String) -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(MHDimensions.corner.dp),
        backgroundColor = MHColors.cardBackground,
        modifier = Modifier
            .height(MHDimensions.portraitHeight.dp)
            .clickable {
                onClickListener(movieId)
            }
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .width(128.dp)
        ) {
            Image(
                painter = rememberImagePainter(data = posterUrl), contentDescription = null,
                modifier = Modifier.aspectRatio(9f.div(16)),
                contentScale = ContentScale.Crop
            )
            Text(
                movieName ?: "",
                style = MHStyle.body1, modifier = Modifier.padding(
                    top = 8.dp,
                    start = 8.dp, end = 8.dp, bottom = 4.dp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                "$releaseYear $genre",
                style = MHStyle.caption,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            if (rating != null || ratingTotalCountText?.isNotBlank() == true) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("$rating $ratingTotalCountText", style = MHStyle.caption)
                }
            }
        }
    }
}

@Composable
@Preview
fun MoviePortraitWidgetPreview() {
    LazyRow {
        item {
            MoviePortraitWidget(
                movieId = "1234",
                posterUrl = "https://image.tmdb.org/t/p/w780//7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
                movieName = "Avengers: Infinity War",
                releaseYear = 2021,
                genre = "Adventure",
                rating = 9.2f,
                ratingTotalCountText = "(23,24 k)"
            )
        }
        item {
            MoviePortraitWidget(
                movieId = "1234",
                posterUrl = "https://image.tmdb.org/t/p/w780//7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
                movieName = "Avengers: Infinity War",
                releaseYear = 2021,
                genre = "Adventure",
                rating = 9.2f,
                ratingTotalCountText = "(23,24 k)"
            )
        }
    }
}