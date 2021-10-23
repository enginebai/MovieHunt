package com.enginebai.moviehunt.ui.detail.holders

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.base.extensions.setExistence
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.utils.loadImage
import kotlinx.android.synthetic.main.holder_movie_review.view.*

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

    // TODO: bookmark feature

    override fun bind(holder: Holder) {
        super.bind(holder)
        Log.wtf("qwer", avatar)
        holder.imageReviewerAvatar.loadImage(avatar, circular = true)
        holder.textName.text = name
        holder.textRating.setExistence(rating != null)
        holder.textRating.text = "%.1f".format(rating)
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