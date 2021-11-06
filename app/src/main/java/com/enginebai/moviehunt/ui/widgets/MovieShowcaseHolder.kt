package com.enginebai.moviehunt.ui.widgets

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.utils.loadImage

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