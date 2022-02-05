package com.enginebai.moviehunt.ui.widgets

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.resources.MHDimensions
import com.enginebai.moviehunt.resources.MHStyle
import com.enginebai.moviehunt.utils.loadImage
import java.util.*

@EpoxyModelClass(layout = R.layout.holder_movie_showcase)
abstract class MovieShowcaseHolder : EpoxyModelWithHolder<MovieShowcaseHolder.Holder>() {

    @EpoxyAttribute
    lateinit var movieId: String

    @EpoxyAttribute
    var backgroundImageUrl: String? = null

    @EpoxyAttribute
    var backdropUrl: String? = null

    @EpoxyAttribute
    var movieName: String? = null

    @EpoxyAttribute
    var genres: String? = null

    @EpoxyAttribute
    var rating: Float? = null

    @EpoxyAttribute
    var ratingTotalCountText: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var onClickListener: (String) -> Unit = {}

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.rootView.setOnClickListener {
            onClickListener(movieId)
        }
        holder.imageBackdrop.loadImage(backdropUrl)
        holder.imageBackground.loadImage(backgroundImageUrl)
        holder.textName.text = movieName
        holder.textGenres.text = genres
        holder.textRating.text = "%.1f (%s)".format(rating, ratingTotalCountText)
    }

    class Holder : EpoxyHolder() {
        lateinit var rootView: View
        lateinit var imageBackground: ImageView
        lateinit var imageBackdrop: ImageView
        lateinit var textName: TextView
        lateinit var textGenres: TextView
        lateinit var textRating: TextView
        override fun bindView(itemView: View) {
            rootView = itemView
            imageBackground = itemView.findViewById(R.id.imageBackground)
            imageBackdrop = itemView.findViewById(R.id.imageBackdrop)
            textName = itemView.findViewById(R.id.textMovieName)
            textGenres = itemView.findViewById(R.id.textGenres)
            textRating = itemView.findViewById(R.id.textRating)
        }
    }
}

@Composable
fun MovieShowcaseWidget(
    movieId: String,
    backgroundImageUrl: String? = null,
    backdropUrl: String? = null,
    movieName: String? = null,
    genres: String? = null,
    rating: Float? = null,
    ratingTotalCountText: String? = null,
    onClickListener: (String) -> Unit = {}
) {
    Card(modifier = Modifier
        .height(MHDimensions.showcaseHeight)
        .clickable {
            onClickListener.invoke(movieId)
        }) {
        Box {
            Image(
                painter = rememberImagePainter(backgroundImageUrl), contentDescription = null,
                modifier = Modifier.aspectRatio(320f.div(157))
            )
            Column {
                Card {
                    Image(
                        painter = rememberImagePainter(backdropUrl), contentDescription = null,
                        modifier = Modifier.aspectRatio(320f.div(157))
                    )
                }
                Text(
                    movieName ?: "",
                    style = MHStyle.headline6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    genres ?: "",
                    style = MHStyle.body2
                )
                Text("$rating $ratingTotalCountText")
            }
        }
    }
}

@Composable
@Preview
fun MovieShowcaseWidgetPreview() {
    MovieShowcaseWidget(
        movieId = UUID.randomUUID().toString(),
        backdropUrl = "https://image.tmdb.org/t/p/w780//1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
        backgroundImageUrl = "https://image.tmdb.org/t/p/w780//1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
        movieName = "Spider-Man: No Way Home",
        rating = 9.5f,
        ratingTotalCountText = "1.14k review",
        genres = "Actions, Adventure, Science Fiction"
    )
}