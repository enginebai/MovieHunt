package com.enginebai.moviehunt.ui.detail.holders

import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.resources.ColorsPalette
import com.enginebai.moviehunt.resources.MHDimensions
import com.enginebai.moviehunt.resources.MHStyle
import com.enginebai.moviehunt.ui.widgets.BookmarkButton
import com.enginebai.moviehunt.ui.widgets.MovieRatingWidget
import kotlinx.android.synthetic.main.holder_movie_info.view.*
import kotlinx.android.synthetic.main.view_rating.view.*

@EpoxyModelClass(layout = R.layout.holder_movie_info)
abstract class MovieInfoHolder : EpoxyModelWithHolder<MovieInfoHolder.Holder>() {

    @EpoxyAttribute
    var movieId: String? = null

    @EpoxyAttribute
    var name: String? = null

    @EpoxyAttribute
    var rating = 0.0f

    @EpoxyAttribute
    var ratingTotalCountText: String? = null

    @EpoxyAttribute
    var genres: String? = null

    @EpoxyAttribute
    var releaseDateText: String? = null

    @EpoxyAttribute
    var runtimeText: String? = null

    @EpoxyAttribute
    var overview: String? = null

    // TODO: bookmark feature

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.textName.text = name
        holder.ratingBar.rating = rating
        holder.textRating.text =
            holder.textRating.context.getString(R.string.reviews_total_count, ratingTotalCountText)
        holder.textGenres.text = genres
        holder.textReleaseDate.text = releaseDateText
        holder.textRuntime.text = runtimeText
        holder.textOverview.text = overview
    }

    class Holder : EpoxyHolder() {
        lateinit var textName: TextView
        lateinit var ratingBar: RatingBar
        lateinit var textRating: TextView
        lateinit var textGenres: TextView
        lateinit var textReleaseDate: TextView
        lateinit var textRuntime: TextView
        lateinit var textOverview: TextView

        override fun bindView(itemView: View) {
            textName = itemView.textMovieName
            ratingBar = itemView.ratingBar
            textRating = itemView.textRating
            textGenres = itemView.textGenres
            textReleaseDate = itemView.textReleaseDate
            textRuntime = itemView.textRuntime
            textOverview = itemView.textOverview
        }

    }
}

@Composable
fun MovieInfoWidget(
    posterUrl: String? = null,
    movieName: String? = null,
    isBookmark: Boolean = false,
    rating: Float = 0.0f,
    ratingTotalCountText: String? = null,
    genres: String? = null,
    releaseDateText: String? = null,
    runtimeText: String? = null,
    overview: String? = null,
    onBackButtonClicked: () -> Unit = {}
) {
    Column {
        Box {
            Image(
                painter = rememberImagePainter(data = posterUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = 3.0f.div(4.0f)),
                contentScale = ContentScale.Crop
            )
            IconButton(onClick = onBackButtonClicked, modifier = Modifier.padding(start = 8.dp, top = 8.dp)) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(12.dp)
                    ,
                    tint = Color.White
                )
            }
        }
        Column(modifier = Modifier.padding(start = MHDimensions.pagePadding.dp, end = 8.dp)) {
            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    movieName ?: "",
                    style = MHStyle.headline5,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                )
                BookmarkButton(isBookmark = isBookmark, onCheckedChange = {
                    // TODO: bookmark feature
                })
            }
            MovieRatingWidget(
                rating = rating,
                textRating = stringResource(R.string.reviews_total_count, ratingTotalCountText ?: "")
            )
            Text(
                genres ?: "",
                style = MHStyle.caption.copy(color = ColorsPalette.grey),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                releaseDateText ?: "",
                style = MHStyle.caption.copy(color = ColorsPalette.grey),
                modifier = Modifier.padding(top = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.clock), contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    runtimeText ?: ",",
                    style = MHStyle.caption.copy(color = ColorsPalette.colorAccent),
                )
            }
            if (!overview.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(stringResource(id = R.string.title_overview), style = MHStyle.headline6)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    overview, style = MHStyle.body2,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun MovieInfoWidgetPreview() {
    MovieInfoWidget(
        posterUrl = "https://image.tmdb.org/t/p/w780//or06FN3Dka5tukK1e9sl16pB3iy.jpg",
        movieName = "Star Wars",
        isBookmark = false,
        rating = 4.3f,
        ratingTotalCountText = "10 k",
        genres = "Action, Science Fictions, Adventure",
        releaseDateText = "2021-08-12",
        runtimeText = "1h 12m",
        overview = "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain."
    )
}