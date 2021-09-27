package com.enginebai.moviehunt.ui.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.moviehunt.R
import java.text.MessageFormat

@EpoxyModelClass(layout = R.layout.holder_movie_portrait)
abstract class MoviePortraitHolder : EpoxyModelWithHolder<MoviePortraitHolder.Holder>() {

    @EpoxyAttribute
    lateinit var movieId: String

    @EpoxyAttribute
    var posterUrl: String? = null

    @EpoxyAttribute
    var movieName: String? = null

    @EpoxyAttribute
    var releaseYear: Int? = null

    @EpoxyAttribute
    var genre: String? = null

    @EpoxyAttribute
    var rating: Float? = null

    @EpoxyAttribute
    var ratingTotalCount: Int? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var onClickListener: (String) -> Unit = {}

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.rootView.setOnClickListener {
            onClickListener(movieId)
        }
        holder.imagePoster.load(posterUrl)
        holder.textName.text = movieName
        holder.textReleaseYearAndGenre.text = "%s %s".format(releaseYear, genre)
        holder.textRating.text = "%.1f (%s)".format(rating, ratingTotalCount)
    }

    class Holder : EpoxyHolder() {
        lateinit var rootView: View
        lateinit var imagePoster: ImageView
        lateinit var textName: TextView
        lateinit var textReleaseYearAndGenre: TextView
        lateinit var textRating: TextView
        override fun bindView(itemView: View) {
            rootView = itemView
            imagePoster = itemView.findViewById(R.id.imagePoster)
            textName = itemView.findViewById(R.id.textMovieName)
            textReleaseYearAndGenre = itemView.findViewById(R.id.textReleaseYearAndGenre)
            textRating = itemView.findViewById(R.id.textRating)
        }
    }
}