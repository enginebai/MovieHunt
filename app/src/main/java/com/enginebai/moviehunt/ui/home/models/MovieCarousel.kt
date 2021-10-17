package com.enginebai.moviehunt.ui.home.models

import android.content.Context
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.*
import com.enginebai.moviehunt.R

@ModelView(saveViewState = true, autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class MovieCarousel(context: Context) : Carousel(context) {

    @ModelProp(ModelProp.Option.DoNotHash)
    fun setEpoxyController(controller: EpoxyController) {
        setControllerAndBuildModels(controller)
    }

    // you need to override this to prevent NPE of Carousel.setModels()
    @ModelProp
    override fun setModels(models: List<EpoxyModel<*>>) {
        // remove super method because we use PagedController for models build.
    }
}