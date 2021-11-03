package com.enginebai.moviehunt.ui.detail.holders

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.utils.loadImage
import kotlinx.android.synthetic.main.holder_movie_info.view.*
import kotlinx.android.synthetic.main.holder_movie_trailer.view.*
import kotlinx.android.synthetic.main.view_rating.view.*

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