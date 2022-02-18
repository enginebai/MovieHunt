package com.enginebai.moviehunt.ui.widgets

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.resources.MHColors
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
    Card(
        modifier = Modifier
            .height(MHDimensions.showcaseHeight.dp)
            .aspectRatio(
                MHDimensions.showcaseWidth
                    .toFloat()
                    .div(MHDimensions.showcaseHeight)
            )
            .clickable {
                onClickListener.invoke(movieId)
            },
        backgroundColor = MHColors.cardBackground
    ) {
        Box {
            Image(
                painter = rememberImagePainter(backgroundImageUrl), contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(MHColors.showcaseOverlay)
            )
            Column(
                modifier = Modifier.padding(
                    horizontal = MHDimensions.pagePadding.dp,
                    vertical = 12.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(shape = RoundedCornerShape(MHDimensions.cornerLarge.dp)) {
                    Image(
                        painter = rememberImagePainter(backdropUrl), contentDescription = null,
                        modifier = Modifier
                            .height(MHDimensions.showcaseImageHeight.dp)
                            .aspectRatio(
                                MHDimensions.showcaseImageWidth
                                    .toFloat()
                                    .div(MHDimensions.showcaseImageHeight)
                            )
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    movieName ?: "",
                    style = MHStyle.headline6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                Text(
                    genres ?: "",
                    style = MHStyle.body2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "$rating ($ratingTotalCountText)",
                        style = MHStyle.caption,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
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