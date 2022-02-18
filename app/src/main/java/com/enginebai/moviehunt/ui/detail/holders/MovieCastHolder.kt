package com.enginebai.moviehunt.ui.detail.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.resources.MHDimensions
import com.enginebai.moviehunt.resources.MHStyle
import com.enginebai.moviehunt.utils.loadImage
import kotlinx.android.synthetic.main.holder_movie_cast.view.*

@EpoxyModelClass(layout = R.layout.holder_movie_cast)
abstract class MovieCastHolder : EpoxyModelWithHolder<MovieCastHolder.Holder>() {

    @EpoxyAttribute
    var avatar: String? = null

    @EpoxyAttribute
    var actorName: String? = null

    @EpoxyAttribute
    var character: String? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.imageCastAvatar.loadImage(avatar, circular = true)
        holder.textActorName.text = actorName
        holder.textCharacter.text = character
    }

    class Holder : EpoxyHolder() {
        lateinit var imageCastAvatar: ImageView
        lateinit var textActorName: TextView
        lateinit var textCharacter: TextView

        override fun bindView(itemView: View) {
            this.imageCastAvatar = itemView.imageCastAvatar
            textActorName = itemView.textActorName
            textCharacter = itemView.textCharacter
        }

    }
}

@Composable
fun MovieCastWidget(
    avatar: String? = null,
    actorName: String? = null,
    character: String? = null
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(
                data = avatar,
                builder = {
                    transformations(CircleCropTransformation())
                },
            ),
            contentDescription = null,
            modifier = Modifier
                .size(MHDimensions.avatarCast.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.height(MHDimensions.avatarCast.dp)
        ) {
            actorName?.let {
                Text(it, style = MHStyle.subtitle2)
            }
            character?.let {
                Text(it, style = MHStyle.caption)
            }
        }
    }
}

@Composable
@Preview
fun MovieCastWidgetPreview() {
    MovieCastWidget(
        avatar = "https://image.tmdb.org/t/p/w185//fysvehTvU6bE3JgxaOTRfvQJzJ4.jpg",
        actorName = "Gal Gadot",
        character = "The Bishop"
    )
}