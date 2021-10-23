package com.enginebai.moviehunt.ui.detail.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.utils.loadImage
import kotlinx.android.synthetic.main.holder_movie_cast.view.*

@EpoxyModelClass(layout = R.layout.holder_movie_cast)
abstract class MovieCastHolder : EpoxyModelWithHolder<MovieCastHolder.Holder>() {

    @EpoxyAttribute
    var avatar: String? = null

    @EpoxyAttribute
    var actorName: String? = null

    @EpoxyAttribute
    var character: String? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.imageCastAvatar.loadImage(avatar, circular = true)
        holder.textActorName.text = actorName
        holder.textCharacter.text = character
    }

    class Holder : EpoxyHolder() {
        lateinit var imageCastAvatar: ImageView
        lateinit var textActorName: TextView
        lateinit var textCharacter: TextView

        override fun bindView(itemView: View) {
            this.imageCastAvatar = itemView.imageCastAvatar
            textActorName = itemView.textActorName
            textCharacter = itemView.textCharacter
        }

    }
}