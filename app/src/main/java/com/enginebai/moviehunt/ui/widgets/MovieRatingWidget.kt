package com.enginebai.moviehunt.ui.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enginebai.moviehunt.resources.MHColors
import com.enginebai.moviehunt.resources.MHStyle
import com.gowtham.ratingbar.RatingBar

@Composable
fun MovieRatingWidget(
    rating: Float = 0.0f,
    textRating: String? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        RatingBar(
            value = rating,
            onValueChange = {},
            onRatingChanged = {},
            isIndicator = true,
            numStars = 5,
            size = 12.dp,
            activeColor = MHColors.ratingBarActive,
            inactiveColor = MHColors.ratingBarInactive,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(rating.toString(), style = MHStyle.caption)
        Spacer(modifier = Modifier.width(4.dp))
        Text(textRating ?: "", style = MHStyle.caption)
    }
}

@Composable
@Preview
fun MovieRatingWidgetPreview() {
    MovieRatingWidget(rating = 4.3f, textRating = "(1.68 k reviews)")
}