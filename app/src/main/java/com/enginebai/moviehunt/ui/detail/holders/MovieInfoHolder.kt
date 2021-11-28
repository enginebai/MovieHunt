package com.enginebai.moviehunt.ui.detail.holders

import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.resources.ColorsPalette
import com.enginebai.moviehunt.resources.MHDimensions
import com.enginebai.moviehunt.resources.MHStyle
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
    movieName: String,
    isBookmark: Boolean
) {
    Column(modifier = Modifier.padding(start = MHDimensions.pagePadding, end = 8.dp)) {
        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                movieName, style = MHStyle.headline5,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
            )
            IconToggleButton(
                modifier = Modifier.padding(8.dp),
                checked = isBookmark,
                onCheckedChange = {
                    // TODO:
                }) {
                Icon(
                    painter = painterResource(id = if (isBookmark) R.drawable.ic_bookmarked else R.drawable.ic_bookmark),
                    contentDescription = null,
                    tint = ColorsPalette.colorAccent
                )
            }
        }
    }
}

@Preview
@Composable
fun MovieInfoWidgetPreview() {
    MovieInfoWidget(movieName = "Star Wars", isBookmark = true)
}