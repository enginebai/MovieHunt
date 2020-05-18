package com.enginebai.moviehunt.ui.home

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.ui.list.MovieCategory

@EpoxyModelClass(layout = R.layout.header_movie_category)
abstract class CategoryHeaderHolder : EpoxyModelWithHolder<CategoryHeaderHolder.Holder>() {

	@EpoxyAttribute
	lateinit var category: MovieCategory
	@EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) var clickListener: OnHeaderClickListener? = null

	interface OnHeaderClickListener {
		fun onViewAllClicked(category: MovieCategory)
	}

	override fun bind(holder: Holder) {
		holder.textTitle.setText(category.strRes)
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

