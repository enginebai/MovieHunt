package com.enginebai.moviehunt.ui.widgets

import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.PLACEHOLDER
import com.enginebai.moviehunt.data.remote.Review
import com.enginebai.moviehunt.resources.MHColors
import com.enginebai.moviehunt.resources.MHDimensions
import com.enginebai.moviehunt.resources.MHStyle
import com.enginebai.moviehunt.utils.DateTimeFormatter.format
import com.enginebai.moviehunt.utils.loadImage
import kotlinx.android.synthetic.main.holder_movie_review.view.*
import java.util.*

fun Review.toHolder(): MovieReviewHolder_ {
    return MovieReviewHolder_()
        .id("${MovieReviewHolder::class.java.simpleName} ${this.id}")
        .avatar(this.author?.getAvatarFullPath())
        .name(this.author?.username)
        .rating(this.author?.rating)
        .comment(this.content)
        .createdAtDateText(this.createdAt?.format())
}

@EpoxyModelClass(layout = R.layout.holder_movie_review)
abstract class MovieReviewHolder : EpoxyModelWithHolder<MovieReviewHolder.Holder>() {

    @EpoxyAttribute
    var movieId: String? = null

    @EpoxyAttribute
    var avatar: String? = null

    @EpoxyAttribute
    var name: String? = null

    @EpoxyAttribute
    var createdAtDateText: String? = null

    @EpoxyAttribute
    var rating: Float? = null

    @EpoxyAttribute
    var comment: String? = null


    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.imageReviewerAvatar.loadImage(avatar, circular = true)
        holder.textName.text = name
        holder.textRating.text = rating?.let { "%.1f".format(rating) } ?: run { PLACEHOLDER }
        holder.textReleaseDate.text = createdAtDateText
        holder.textComment.text = comment
    }

    class Holder : EpoxyHolder() {
        lateinit var imageReviewerAvatar: ImageView
        lateinit var textName: TextView
        lateinit var textRating: TextView
        lateinit var textReleaseDate: TextView
        lateinit var textComment: TextView

        override fun bindView(itemView: View) {
            imageReviewerAvatar = itemView.imageReviewerAvatar
            textName = itemView.textReviewerName
            textRating = itemView.textReviewRating
            textReleaseDate = itemView.textDate
            textComment = itemView.textReviewComment
        }

    }
}

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