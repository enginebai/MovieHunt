package com.enginebai.moviehunt.ui.home.models

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.enginebai.moviehunt.R

@EpoxyModelClass(layout = R.layout.view_home_load_init)
abstract class HomeLoadInitView : EpoxyModel<View>()

@EpoxyModelClass(layout = R.layout.view_home_load_more)
abstract class HomeLoadMoreView : EpoxyModel<View>()