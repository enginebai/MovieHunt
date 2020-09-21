package com.enginebai.moviehunt.ui.list

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.enginebai.moviehunt.R

@EpoxyModelClass(layout = R.layout.holder_movie_landscape)
abstract class MovieListEpoxyModel : EpoxyModelWithHolder<MovieListEpoxyModel.Holder>() {

    @EpoxyAttribute
    var movieId = ""
    @EpoxyAttribute
    var imagePoster = ""
    @EpoxyAttribute
    var textTitle = "--"
    @EpoxyAttribute
    var rating = 0.0f
    @EpoxyAttribute
    var voteCount = ""
    @EpoxyAttribute
    var duration = ""
    @EpoxyAttribute
    var releaseDate = ""
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var itemClickListener: (String) -> Unit = {}

    override fun bind(holder: Holder) {
        Glide.with(holder.imagePoster)
            .load(imagePoster)
            .error(R.color.darkBlue)
            .placeholder(R.color.darkBlue)
            .into(holder.imagePoster)
        holder.textTitle.text = textTitle
        holder.ratingBar.rating = rating
        holder.textVoteCount.text = voteCount
        holder.textDuration.text = duration
        holder.textReleaseDate.text = releaseDate
        holder.itemView.setOnClickListener { itemClickListener(movieId) }
    }

    class Holder : EpoxyHolder() {

        lateinit var itemView: View
        lateinit var imagePoster: ImageView
        lateinit var textTitle: TextView
        lateinit var ratingBar: RatingBar
        lateinit var textVoteCount: TextView
        lateinit var textDuration: TextView
        lateinit var textReleaseDate: TextView

        override fun bindView(itemView: View) {
            this.itemView = itemView
            imagePoster = itemView.findViewById(R.id.imagePoster)
            textTitle = itemView.findViewById(R.id.textTitle)
            ratingBar = itemView.findViewById(R.id.ratingBar)
            textVoteCount = itemView.findViewById(R.id.textVoteCount)
            textDuration = itemView.findViewById(R.id.textDuration)
            textReleaseDate = itemView.findViewById(R.id.textReleaseDate)
        }

    }
}