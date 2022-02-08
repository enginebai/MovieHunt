package com.enginebai.moviehunt.ui.detail.holders

import android.view.View
import android.widget.ImageView
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
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.resources.MHDimensions
import com.enginebai.moviehunt.utils.loadImage
import kotlinx.android.synthetic.main.holder_movie_trailer.view.*
import timber.log.Timber

@EpoxyModelClass(layout = R.layout.holder_movie_trailer)
abstract class MovieTrailerHolder : EpoxyModelWithHolder<MovieTrailerHolder.Holder>() {

    @EpoxyAttribute
    var thumbnail: String? = null

    @EpoxyAttribute
    var trailerUrl: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var onTrailerPlayed: (String) -> Unit = {}

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.imageTrailerThumbnail.loadImage(thumbnail)
        holder.imageTrailerThumbnail.setOnClickListener {
            trailerUrl?.let {
                onTrailerPlayed(it)
            }
        }
    }

    class Holder : EpoxyHolder() {
        lateinit var imageTrailerThumbnail: ImageView

        override fun bindView(itemView: View) {
            this.imageTrailerThumbnail = itemView.imageTrailerThumbnail
        }

    }
}

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