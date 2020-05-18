package com.enginebai.moviehunt.ui.home

import android.content.Context
import com.airbnb.epoxy.*

@ModelView(saveViewState = true, autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class MovieCarousel(context: Context) : Carousel(context) {

	@ModelProp(ModelProp.Option.DoNotHash)
	fun setEpoxyController(controller: EpoxyController) {
		setControllerAndBuildModels(controller)
	}

	// you need to override this to prevent NPE of Carousel.setModels()
	@ModelProp
	override fun setModels(models: List<EpoxyModel<*>>) {
		super.setModels(models)
	}
}