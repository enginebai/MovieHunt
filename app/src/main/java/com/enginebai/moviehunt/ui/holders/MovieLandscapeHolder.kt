package com.enginebai.moviehunt.ui.holders

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import coil.load
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.PLACEHOLDER

@EpoxyModelClass(layout = R.layout.holder_movie_landscape)
abstract class MovieListEpoxyModel : EpoxyModelWithHolder<MovieListEpoxyModel.Holder>() {

    @EpoxyAttribute
    var movieId = ""
    @EpoxyAttribute
    var imagePoster = ""
    @EpoxyAttribute
    var textTitle = PLACEHOLDER
    @EpoxyAttribute
    var rating = 0.0f
    @EpoxyAttribute
    var duration = ""
    @EpoxyAttribute
    var releaseYear: Int? = null
    @EpoxyAttribute
    var genre: String? = null
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var itemClickListener: (String) -> Unit = {}

    override fun bind(holder: Holder) {
        holder.imagePoster
            .load(imagePoster) {
                error(R.color.darkBlue)
                placeholder(R.color.darkBlue)
            }
        holder.textTitle.text = textTitle
        holder.ratingBar.rating = rating
        holder.textRating.text = rating.toString()
        holder.textDuration.text = duration
        holder.textReleaseYearAndGenre.text = "$releaseYear $genre"
        holder.itemView.setOnClickListener { itemClickListener(movieId) }
    }

    class Holder : EpoxyHolder() {

        lateinit var itemView: View
        lateinit var imagePoster: ImageView
        lateinit var textTitle: TextView
        lateinit var ratingBar: RatingBar
        lateinit var textRating: TextView
        lateinit var textDuration: TextView
        lateinit var textReleaseYearAndGenre: TextView
        lateinit var buttonBookmark: ImageButton

        override fun bindView(itemView: View) {
            this.itemView = itemView
            imagePoster = itemView.findViewById(R.id.imagePoster)
            textTitle = itemView.findViewById(R.id.textTitle)
            ratingBar = itemView.findViewById(R.id.ratingBar)
            textRating = itemView.findViewById(R.id.textRating)
            textDuration = itemView.findViewById(R.id.textDuration)
            textReleaseYearAndGenre = itemView.findViewById(R.id.textReleaseYearAndGenre)
            buttonBookmark = itemView.findViewById(R.id.buttonBookmark)
        }

    }
}