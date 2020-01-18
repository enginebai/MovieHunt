package com.enginebai.moviehunt.ui.movie.list

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.enginebai.moviehunt.R

@EpoxyModelClass(layout = R.layout.view_load_more)
abstract class LoadMoreView : EpoxyModel<View>()