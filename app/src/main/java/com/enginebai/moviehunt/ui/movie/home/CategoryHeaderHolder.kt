package com.enginebai.moviehunt.ui.movie.home

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.moviehunt.R

@EpoxyModelClass(layout = R.layout.header_movie_category)
abstract class CategoryHeaderHolder : EpoxyModelWithHolder<CategoryHeaderHolder.Holder>() {

    @EpoxyAttribute var category = ""
    @EpoxyAttribute var title = ""
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) var clickListener: OnHeaderClickListener? = null

    interface OnHeaderClickListener {
        fun onViewAllClicked(category: String)
    }

    override fun bind(holder: Holder) {
        holder.textTitle.text = title
        holder.buttonViewAll.setOnClickListener { clickListener?.onViewAllClicked(category) }
    }

    override fun unbind(holder: Holder) {
        holder.buttonViewAll.setOnClickListener(null)
    }

    class Holder : EpoxyHolder() {
        lateinit var textTitle: TextView
        lateinit var buttonViewAll: Button

        override fun bindView(itemView: View) {
            textTitle = itemView.findViewById(R.id.textCategoryHeader)
            buttonViewAll = itemView.findViewById(R.id.buttonViewAll)
        }
    }
}

