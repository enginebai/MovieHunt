package com.enginebai.moviehunt.ui.movie.home

import android.content.Context
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView

@ModelView(saveViewState = true, autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class MovieCarousel(context: Context) : Carousel(context) {

    @ModelProp(ModelProp.Option.DoNotHash)
    fun setEpoxyController(controller: EpoxyController) {
        setControllerAndBuildModels(controller)
    }
}