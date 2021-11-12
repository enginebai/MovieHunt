package com.enginebai.moviehunt.ui.widgets

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.PLACEHOLDER
import com.enginebai.moviehunt.data.remote.Review
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
    movieId: String? = null,
    avatar: String? = null,
    name: String? = null,
    createdAtDateText: String? = null,
    rating: Float? = null,
    comment: String? = null
) {
    Column(
        Modifier.padding(
            start = dimensionResource(id = R.dimen.page_horizontal_padding),
            end = dimensionResource(id = R.dimen.page_horizontal_padding),
            top = dimensionResource(id = R.dimen.size_8),
            bottom = dimensionResource(id = R.dimen.size_4)
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
                    .width(dimensionResource(id = R.dimen.avatar_review))
                    .height(dimensionResource(id = R.dimen.avatar_review))
                    .background(colorResource(id = R.color.cardBackground), shape = CircleShape)
            )
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.size_8)))
            Column {
                Text(text = name ?: "")
                Text(text = createdAtDateText ?: "")
            }
            Text(
                // TODO: background
                // TODO: star icon
                text = rating?.let { "%.1f".format(rating) } ?: run { PLACEHOLDER },
            )
        }
        Text(text = comment ?: "")
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun MovieReviewPreview() {
    MovieReviewWidget(
        movieId = "1234",
        name = "Robert",
        avatar = "https://image.tmdb.org/t/p/w500//4DiJQ1mBp4ztoznZADIrPg69v46.jpg",
        createdAtDateText = Calendar.getInstance().format(),
        rating = 9.5f,
        comment = "The character development for Thanos was so good that it made me think that maybe he was right. He was the villain that surpassed all the other villains from the past Marvel movies. Trust me, this is the movie that might have changed the MCU."
    )
}