package com.enginebai.moviehunt.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.PLACEHOLDER
import com.enginebai.moviehunt.resources.ColorsPalette
import com.enginebai.moviehunt.resources.MHColors
import com.enginebai.moviehunt.resources.MHDimensions
import com.enginebai.moviehunt.resources.MHStyle
import java.util.*

@Composable
fun MovieLandscapeWidget(
    movieId: String,
    imagePoster: String = "",
    textTitle: String = PLACEHOLDER,
    isBookmark: Boolean = false,
    rating: Float = 0.0f,
    ratingTotalCountText: String? = null,
    releaseDateText: String? = null,
    genre: String? = null,
    itemClickListener: (String) -> Unit = {},
    onBookmarkChanged: (Boolean) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .clickable { itemClickListener(movieId) },
        backgroundColor = MHColors.cardBackground
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, bottom = 12.dp, end = 8.dp)
        ) {
            Card(
                shape = RoundedCornerShape(MHDimensions.corner.dp),
                modifier = Modifier.padding(end = 12.dp, top = 8.dp)
            ) {
                Image(
                    painter = rememberImagePainter(data = imagePoster), contentDescription = null,
                    modifier = Modifier
                        .height(MHDimensions.posterHeight.dp)
                        .aspectRatio(9f.div(16f)),
                    contentScale = ContentScale.Crop
                )
            }
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        textTitle,
                        style = MHStyle.headline6,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    BookmarkButton(isBookmark = isBookmark, onCheckedChange = onBookmarkChanged)
                }
                releaseDateText?.let {
                    Text(
                        releaseDateText,
                        style = MHStyle.body2.copy(color = ColorsPalette.colorAccent),
                    )
                }
                genre?.let {
                    Text(
                        genre,
                        style = MHStyle.caption,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                MovieRatingWidget(
                    rating = rating,
                    textRating = stringResource(
                        R.string.reviews_total_count,
                        ratingTotalCountText ?: ""
                    )
                )
            }
        }
    }
}

@Composable
@Preview
fun MovieLandscapeWidgetPreview() {
    MovieLandscapeWidget(
        movieId = UUID.randomUUID().toString(),
        imagePoster = "https://image.tmdb.org/t/p/w780//1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
        textTitle = "Spider-Man: No Way Home",
        isBookmark = true,
        rating = 9.5f,
        ratingTotalCountText = "1.14k review",
        releaseDateText = "2021-12-15",
        genre = "Actions, Adventure, Science Fiction"
    )
}