package com.enginebai.moviehunt.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.PLACEHOLDER
import com.enginebai.moviehunt.resources.MHColors
import com.enginebai.moviehunt.resources.MHDimensions
import com.enginebai.moviehunt.resources.MHStyle
import com.enginebai.moviehunt.utils.DateTimeFormatter.format
import java.util.*

@Composable
fun MovieReviewWidget(
    avatar: String? = null,
    name: String? = null,
    createdAtDateText: String? = null,
    rating: Float? = null,
    comment: String? = null
) {
    Column(
        Modifier.background(MHColors.cardBackground).padding(
            start = MHDimensions.pagePadding.dp,
            end = MHDimensions.pagePadding.dp,
            top = 12.dp,
            bottom = 12.dp
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberImagePainter(
                    data = avatar,
                    builder = {
                        transformations(CircleCropTransformation())
                    }),
                contentDescription = null,
                modifier = Modifier
                    .width(MHDimensions.avatarReview.dp)
                    .height(MHDimensions.avatarReview.dp)
                    .background(MHColors.cardBackground, shape = CircleShape)
            )
            Spacer(modifier = Modifier.size(12.dp))
            Column {
                Text(text = name ?: "", style = MHStyle.subtitle2)
                Text(text = createdAtDateText ?: "", style = MHStyle.caption)
            }
            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .background(
                        color = MHColors.ratingBackground,
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(top = 4.dp, bottom = 4.dp, start = 12.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.ic_star_large), contentDescription = null)
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = rating?.let { "%.1f".format(rating) } ?: run { PLACEHOLDER },
                    style = MHStyle.caption,
                )
            }
        }
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = comment ?: "",
            style = MHStyle.body2,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun MovieReviewWidgetPreview() {
    MovieReviewWidget(
        name = "Robert",
        avatar = "https://image.tmdb.org/t/p/w500/4DiJQ1mBp4ztoznZADIrPg69v46.jpg",
        createdAtDateText = Calendar.getInstance().format(),
        rating = 9.5f,
        comment = "The character development for Thanos was so good that it made me think that maybe he was right. He was the villain that surpassed all the other villains from the past Marvel movies. Trust me, this is the movie that might have changed the MCU."
    )
}