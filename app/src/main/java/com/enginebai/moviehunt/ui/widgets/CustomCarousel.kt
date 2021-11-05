package com.enginebai.moviehunt.ui.widgets

import android.content.Context
import android.view.Gravity
import androidx.recyclerview.widget.SnapHelper
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CustomCarousel(context: Context) : Carousel(context) {

    override fun getSnapHelperFactory(): SnapHelperFactory {
        return object : SnapHelperFactory() {
            override fun buildSnapHelper(context: Context?): SnapHelper {
                return GravitySnapHelper(Gravity.START)
            }
        }
    }
}