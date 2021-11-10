package com.enginebai.moviehunt.ui.widgets

import android.view.View
import android.widget.ImageView
import android.widget.TextView
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