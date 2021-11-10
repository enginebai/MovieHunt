package com.enginebai.moviehunt.ui.widgets

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.base.extensions.setExistence
import com.enginebai.moviehunt.R

@EpoxyModelClass(layout = R.layout.holder_title)
abstract class TitleHolder : EpoxyModelWithHolder<TitleHolder.Holder>() {

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var onClickListener: (() -> Unit)? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.textTitle.text = title
        holder.buttonSeeAll.setExistence(onClickListener != null)
        holder.buttonSeeAll.setOnClickListener { onClickListener?.invoke() }
    }

    class Holder : EpoxyHolder() {
        lateinit var textTitle: TextView
        lateinit var buttonSeeAll: Button
        override fun bindView(itemView: View) {
            textTitle = itemView.findViewById(R.id.textTitle)
            buttonSeeAll = itemView.findViewById(R.id.buttonSeeAll)
        }
    }
}