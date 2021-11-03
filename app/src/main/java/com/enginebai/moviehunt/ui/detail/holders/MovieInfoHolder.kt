package com.enginebai.moviehunt.ui.detail.holders

import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.moviehunt.R
import kotlinx.android.synthetic.main.holder_movie_info.view.*
import kotlinx.android.synthetic.main.view_rating.view.*

@EpoxyModelClass(layout = R.layout.holder_movie_info)
abstract class MovieInfoHolder : EpoxyModelWithHolder<MovieInfoHolder.Holder>() {

    @EpoxyAttribute
    var movieId: String? = null

    @EpoxyAttribute
    var name: String? = null

    @EpoxyAttribute
    var rating = 0.0f

    @EpoxyAttribute
    var ratingTotalCountText: String? = null

    @EpoxyAttribute
    var genres: String? = null

    @EpoxyAttribute
    var releaseDateText: String? = null

    @EpoxyAttribute
    var runtimeText: String? = null

    @EpoxyAttribute
    var overview: String? = null

    // TODO: bookmark feature

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.textName.text = name
        holder.ratingBar.rating = rating
        holder.textRating.text = holder.textRating.context.getString(R.string.reviews_total_count, ratingTotalCountText)
        holder.textGenres.text = genres
        holder.textReleaseDate.text = releaseDateText
        holder.textRuntime.text = runtimeText
        holder.textOverview.text = overview
    }

    class Holder : EpoxyHolder() {
        lateinit var textName: TextView
        lateinit var ratingBar: RatingBar
        lateinit var textRating: TextView
        lateinit var textGenres: TextView
        lateinit var textReleaseDate: TextView
        lateinit var textRuntime: TextView
        lateinit var textOverview: TextView

        override fun bindView(itemView: View) {
            textName = itemView.textMovieName
            ratingBar = itemView.ratingBar
            textRating = itemView.textRating
            textGenres = itemView.textGenres
            textReleaseDate = itemView.textReleaseDate
            textRuntime = itemView.textRuntime
            textOverview = itemView.textOverview
        }

    }
}