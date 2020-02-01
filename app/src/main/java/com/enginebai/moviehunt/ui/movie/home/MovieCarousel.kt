package com.enginebai.moviehunt.ui.movie.home

import android.content.Context
import com.airbnb.epoxy.*

@ModelView(saveViewState = true, autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class MovieCarousel(context: Context) : Carousel(context) {

    @ModelProp(ModelProp.Option.DoNotHash)
    fun setEpoxyController(controller: EpoxyController) {
        setControllerAndBuildModels(controller)
    }

    // this is for preventing NPE of Carousel.setModels()
    @ModelProp
    override fun setModels(models: List<EpoxyModel<*>>) {
    }
}